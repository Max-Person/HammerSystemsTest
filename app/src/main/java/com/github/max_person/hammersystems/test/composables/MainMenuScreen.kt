package com.github.max_person.hammersystems.test.composables

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.github.max_person.hammersystems.test.R
import com.github.max_person.hammersystems.test.data.MenuViewModel
import com.github.max_person.hammersystems.test.data.models.FoodCard
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

val horizontalPadding = 10.dp

@Preview
@Composable
fun MainMenuScreen (
    viewModel : MenuViewModel = hiltViewModel()
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            TopControls()

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
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {

                        val categories by remember{viewModel.categories.data}
                        val categoriesLoading by remember{viewModel.categories.isLoading}
                        LaunchedEffect(Unit){
                            viewModel.categories.load(Unit)
                        }

                        var selectedCategory by remember{viewModel.selectedCategory}

                        val listState = rememberLazyListState()

                        val foods by remember{viewModel.foods.data}
                        var foodsLoading by remember{viewModel.foods.isLoading}
                        LaunchedEffect(selectedCategory){
                            println("launched for category $selectedCategory")
                            if(selectedCategory != null)
                                viewModel.foods.load(selectedCategory!!)
                        }


                        HorizontalButtonSelection(
                            values = categories.map { it.name },
                            modifier = Modifier.padding(bottom = 5.dp),
                            onChange = {selected ->
                                selectedCategory = categories.first{it.name == selected}
                                foodsLoading = true
                                listState.dispatchRawDelta(Float.NEGATIVE_INFINITY)
                                
                            }
                        )

                        if(categoriesLoading || foodsLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ){
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        else{
                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .padding(horizontal = horizontalPadding)
                                    .fillMaxWidth(),
                            ) {
                                items(foods.toList()) { item ->
                                    FoodCard(item)
                                }
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
    var selected by remember(values) {
        mutableStateOf(values.getOrNull(selectedInitial)?.apply(onChange) ?: "")
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
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(selected == it) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.background,
                        contentColor = if(selected == it) MaterialTheme.colorScheme.background
                            else MaterialTheme.colorScheme.onBackground,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(3.dp),
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
fun FoodCard(
    info: FoodCard,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 20.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(info.imageUrl)
                .crossfade(true)
                .build(),
            loading = {CircularProgressIndicator()},
            error = {Icons.Default.Photo},
            contentDescription = info.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = info.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = info.description,
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.outlinedButtonColors(),
                shape = RoundedCornerShape(8.dp),
//                elevation = ButtonDefaults.buttonElevation(3.dp),
                onClick = {},
            ) {
                Text(info.price.toString())
            }
        }
    }

}

@Composable
fun TopControls(){
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
        items(listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
        )){
            Card(
                modifier = Modifier
                    .width(320.dp)
                    .height(120.dp)
            ){
                Image(
                    painter = painterResource(it),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
