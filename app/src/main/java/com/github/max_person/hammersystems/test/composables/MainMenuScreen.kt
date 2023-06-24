package com.github.max_person.hammersystems.test.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

val horizontalPadding = 10.dp

@Preview
@Composable
fun MainMenuScreen (
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 10.dp),
            ){
                Button(
                    onClick = { /* Do something! */ },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    colors = ButtonDefaults.outlinedButtonColors(),
                ) {
                    Text("Moscow")
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "City selector dropdown",
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                            .padding(top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* Do something! */ },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    colors = ButtonDefaults.outlinedButtonColors(),
                ) {
                    Icon(
                        Icons.Default.QrCode,
                        contentDescription = "Scan QR code",
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                    )
                }
            }

            val collapseState = rememberCollapsingToolbarScaffoldState()
            Surface {
                CollapsingToolbarScaffold(
                    state = collapseState, // provide the state of the scaffold
                    modifier = Modifier,
                    scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed, // EnterAlways, EnterAlwaysCollapsed, ExitUntilCollapsed are available
                    toolbar = {
                        SpecialOfferScroll(
                            modifier = Modifier.padding(bottom = 15.dp)
                        )
                    },
                ) {
                    Column {
                        val listState = rememberLazyListState()

                        HorizontalButtonSelection(
                            values = (1..10).toList().map { it.toString() },
                            modifier = Modifier.padding(bottom = 5.dp)
//                                .clip(GenericShape { size, _ ->
//                                    lineTo(size.width, 0f)
//                                    lineTo(size.width, Float.MAX_VALUE)
//                                    lineTo(0f, Float.MAX_VALUE)
//                                })
//                                .shadow((if(listState.canScrollBackward) 10 else 0).dp)
                        ) {

                        }
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .padding(horizontal = horizontalPadding)
                                .fillMaxWidth(),
                        ) {
                            items((1..50).toList()) { item ->
                                Text(modifier = Modifier, text = "Item $item")
                            }
                        }
                    }
                }
            }

        }


    }
}

@Composable
fun HorizontalButtonSelection(
    modifier: Modifier = Modifier,
    values: List<String>,
    selectedInitial: Int = 0,
    onChange: (newValue: String) -> Unit,
){
    var selected by remember {
        mutableStateOf(values[selectedInitial])
    }
    val spacing = 10.dp
    Surface(
        modifier = modifier,
    ){
        LazyRow(
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(spacing),
        ){
            items(values){
                Button(
                    modifier = Modifier
                        .width(95.dp)
                        .selectable(it == selected, onClick = {}),
                    colors = ButtonDefaults.filledTonalButtonColors(),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        selected = it
                        onChange(it)
                    },
                ) {
                    Text(it)
                }
            }
        }
    }

}

@Composable
fun SpecialOfferScroll(
    modifier: Modifier = Modifier
){
    val spacing = 10.dp
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(spacing),
    ){
        items(10){
            SpecialOfferCard()
        }
    }
}

@Composable
fun SpecialOfferCard(){
    Card(
        modifier = Modifier
            .width(320.dp)
            .height(120.dp)
    ){

    }
}