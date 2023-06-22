package com.dicoding.animelist.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.animelist.R
import com.dicoding.animelist.ui.theme.AnimeTheme

@Composable
fun Profile(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 32.dp, horizontal = 32.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.selfie),
            contentDescription = stringResource(R.string.foto_agung_reza_vergiawan),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(16.dp)
                .size(250.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(R.string.agung_reza_vergiawan),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "artze501@gmail.com",
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AboutPreview () {
    AnimeTheme {
        Profile()
    }
}