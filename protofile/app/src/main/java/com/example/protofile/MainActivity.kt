package com.example.protofile


import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.protofile.ui.theme.ProtofileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProtofileTheme {
                // A surface container using the 'background' color from the theme
                val status = remember{
                    mutableStateOf(false)

                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight(),
                    color = MaterialTheme.colors.background
                ) {
                    Card (
                        modifier = Modifier
                            .width(200.dp)
                            .height(390.dp)
                            .padding(12.dp),
                        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                        elevation = 4.dp
                            ){
                        Column(
                            modifier = Modifier.height(300.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            TaoAnh()
                            Divider()
                            CreateInfo()
                            Button(onClick = {
                            status.value =!status.value
                            /*TODO*/ }) {
                                Text(text = "Portfolio")
                            }
                            if(status.value)
                                profile(data = listOf("project 1", "project 2", "project 3"))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateInfo(){
    Column(){
        Text(text = "Phan Thanh Nhan",style= MaterialTheme.typography.h4,color =MaterialTheme.colors.primaryVariant)
        Text(text= "Android pro",modifier = Modifier.padding(3.dp))
        Text(text = "@themeCompose",
        modifier = Modifier.padding(3.dp),
        style = MaterialTheme.typography.subtitle1)

    }
}

@Composable
fun TaoAnh(){
    Surface(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, androidx.compose.ui.graphics.Color.LightGray),
        elevation =  4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)

    ) {
        Image(painter = painterResource(id =  R.drawable.ic_launcher_foreground), contentDescription = "profile image",
        modifier = Modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun profile(data:List<String>){
    LazyColumn{
       items(data){
           item ->
           Card(
               modifier = Modifier
                   .padding(8.dp)
                   .fillMaxWidth(),
               shape = RectangleShape,
               elevation =  4.dp
           ) {
               Row (
                   modifier = Modifier
                       .padding(8.dp)
                       .fillMaxWidth()
                       .background(MaterialTheme.colors.surface),

                       ){
                   TaoAnh()
                   Column(
                       modifier = Modifier
                           .padding(7.dp)
                           .align(Alignment.CenterVertically)
                   ) {

                   }
                   Text(text= item,fontWeight = FontWeight.Bold)
                   Text(text= "great project",style = MaterialTheme.typography.body2)
               }
           }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProtofileTheme {
        Greeting("Android")
    }
}