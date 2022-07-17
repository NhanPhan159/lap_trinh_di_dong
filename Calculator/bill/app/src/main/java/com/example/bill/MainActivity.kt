package com.example.bill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.VectorConverter
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bill.ui.theme.BillTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContent {
            BillTheme {
                // A surface container using the 'background' color from the theme
                var totalmoney by remember { mutableStateOf(TextFieldValue("")) }
                var tienper by remember { mutableStateOf(0.0) }
                var tientip by remember { mutableStateOf(0.0) }
                var tientotal by remember { mutableStateOf(0.0) }
                var sliderPosition by remember { mutableStateOf(0f) }
                var tien by remember { mutableStateOf(0.0) }
                var tip by remember { mutableStateOf(0) }
                val status = remember{
                    mutableStateOf(false)

                }
                val counter = remember {
                    mutableStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Card(
                            modifier = Modifier
                                .padding(40.dp)
                                .width(400.dp),

                            backgroundColor = Color.Cyan
                        ) {


                            Column(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Total Per Person",
                                    style = TextStyle(
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Bold
                                    )
                                )


                                Text(
                                    text = "$${String.format("%.2f",tientotal)}",
                                    style = TextStyle(
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Bold
                                    )
                                )



                            }

                        }

                        Card(
                            modifier = Modifier

                                .animateContentSize( // Animation
                                    animationSpec = tween(
                                        durationMillis = 400, // Animation Speed
                                        easing = LinearOutSlowInEasing // Animation Type
                                    )
                                )
                                .padding(8.dp),


                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(8.dp), // Shap
                            border = BorderStroke(0.5.dp, Color.Yellow),


                        ) {


                            Column(
                                modifier = Modifier
                                   // .height(120.dp)
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    ,
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                OutlinedTextField(
                                    value = totalmoney,
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "emailIcon"
                                        )
                                    },
                                    //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                                    onValueChange = {
                                        totalmoney = it
                                        if (totalmoney.text.equals("")) tien = 0.0
                                        else tien = totalmoney.text.toDouble()
                                    },
                                    label = { Text(text = "Enter your price") },
                                    placeholder = { Text(text = "price") },

                                )
                                Spacer(modifier = Modifier.width(20.dp))


                                if(tien != 0.0) {



                                    Row(modifier = Modifier
                                       // .height(100.dp)
                                        .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start) {
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text(
                                            text = "Split",
                                            style = TextStyle(
                                                color = Color.DarkGray,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )

                                        Spacer(modifier = Modifier.width(100.dp))
                                        CreateCircleUp(counter = counter.value) { newVal ->
                                            counter.value = newVal
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "${counter.value}",
                                            style = TextStyle(
                                                color = Color.DarkGray,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        CreateCircleDown(counter = counter.value) { newVal ->
                                            counter.value = newVal
                                        }

                                    }
                                        Row(modifier = Modifier
                                          //  .height(100.dp)
                                            .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start) {

                                            Text(
                                                text = "Tip",
                                                style = TextStyle(
                                                    color = Color.DarkGray,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )

                                            Spacer(modifier = Modifier.width(120.dp))

                                            Text(
                                                text = "${String.format("%.2f",tientip)}",
                                                style = TextStyle(
                                                    color = Color.DarkGray,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )


                                        }

                                        Column(modifier = Modifier

                                            .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                            ) {

                                            Text(
                                                text = "${sliderPosition.toInt()}%",
                                                style = TextStyle(
                                                    color = Color.DarkGray,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )



                                            Slider(
                                                value = sliderPosition,
                                                onValueChange = { sliderPosition = it},
                                                valueRange = 0f..100f
                                            )



                                        }
                                    }


                            }
                        }
                    tienper = tien / counter.value
                    tientip = sliderPosition.toInt() * tien / 100
                    tientotal = tienper+tientip
                    }




            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun CreateCircleUp(counter : Int,countUp : (Int)->Unit){
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(30.dp)
            .clickable {
                countUp(counter + 1)
            },
        shape = CircleShape,
        elevation = 4.dp
    ){
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(text = "+")
        }
    }
}

@Composable
fun CreateCircleDown(counter : Int,countUp : (Int)->Unit){
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(30.dp)
            .clickable {
                countUp(counter - 1)
            },
        shape = CircleShape,
        elevation = 4.dp
    ){
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(text = "-")
        }
    }
}




@Composable
fun DefaultPreview() {
    BillTheme {
        Greeting("Android")
    }
}
}
