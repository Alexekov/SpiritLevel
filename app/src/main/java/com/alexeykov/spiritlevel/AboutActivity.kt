package com.alexeykov.spiritlevel

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexeykov.spiritlevel.ui.theme.SpiritLevelTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutPreview()
        }
    }
}

@Composable
fun About() {
    Text(
        modifier = Modifier
            .padding(10.dp),
        text = stringResource(id = R.string.app_name),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Text(
        modifier = Modifier
            .padding(10.dp),
        text = stringResource(id = R.string.author),
        fontSize = 16.sp
    )
    Text(
        modifier = Modifier
            .padding(10.dp),
        text = stringResource(id = R.string.version).plus(BuildConfig.VERSION_NAME),
        fontSize = 16.sp
    )
}

@Composable
fun DrawIcon() {
    Image(bitmap = ImageBitmap.imageResource(id = R.drawable.ic_launcher_round),
    contentDescription = "",
    modifier = Modifier.padding(top = 30.dp, bottom = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    val activity = (LocalContext.current as? Activity)
    SpiritLevelTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DrawIcon()
                About()
                Button(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(150.dp),
                    onClick = { activity?.finish() }) {
                    Text(text = stringResource(R.string.OK))
                }
            }
        }
    }
}