package com.sano.ideallist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sano.ideallist.repository.MonsterAbilityData
import com.sano.ideallist.repository.MonsterData
import com.sano.ideallist.R

@Composable
fun DetailScreen(monster: MonsterData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = monster.name,
                    fontSize = 36.sp
                )
                val defaultMonsterIcon = rememberVectorPainter(Icons.Filled.AccountBox)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://www.dnd5eapi.co${monster.imageSrc}")
                        .crossfade(true)
                        .build(),
                    placeholder = defaultMonsterIcon,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(256.dp)
                )
            }
            MonsterAttributes(monster.size, monster.type, monster.alignment)
        }

        Spacer(
            modifier = Modifier
                .height(18.dp)
                .fillMaxWidth()
        )
        AbilitiesPanel(monster.abilities)
    }
}

@Composable
fun MonsterAttributes(
    size: String,
    type: String,
    alignment: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Attributes",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "Size: $size",
            fontSize = dimensionResource(R.dimen.attributes_size).value.sp
        )
        Text(
            text = "Type: $type",
            fontSize = dimensionResource(R.dimen.attributes_size).value.sp
        )
        Text(
            text = "Alignment: $alignment",
            fontSize = dimensionResource(R.dimen.attributes_size).value.sp
        )
    }
}

@Composable
fun AbilityCard(abilityData: MonsterAbilityData, modifier: Modifier = Modifier) {
    Card(
        modifier =
        modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.detail_card_height))
            .padding(bottom = 8.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = abilityData.name,
                fontWeight = FontWeight.Bold,
                modifier =
                Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = abilityData.desc,
                fontSize = dimensionResource(R.dimen.ability_card_font_size).value.sp,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun AbilitiesPanel(abilities: List<MonsterAbilityData>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Abilities: ",
            fontSize = 48.sp
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.large
                )
                .border(
                    4.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.large
                )
        ) {
            items(abilities) {
                AbilityCard(it)
            }
        }
    }

}
//
//@Preview
//@Composable
//fun DetailScreenPreview() {
//    DetailScreen(
//        MonsterData(
//            Icons.Outlined.AccountBox,
//            "Aboleth",
//            "Large",
//            "Abberation",
//            "lawful evil",
//            listOf()
//        )
//    )
//}

@Preview
@Composable
fun AbilityCardPreview() {
    AbilityCard(
        MonsterAbilityData(
            "Amphibious",
            "While underwater, the aboleth is surrounded by transformative mucus. A creature that touches the aboleth or that hits it with a melee attack while within 5 ft. of it must make a DC 14 Constitution saving throw. On a failure, the creature is diseased for 1d4 hours. The diseased creature can breathe only underwater."
        )
    )
}