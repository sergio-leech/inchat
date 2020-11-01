package com.example.inchat.ui.chat_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.inchat.R

@Composable
fun InchatAppBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column {
        // This bar needs to be translucent but, if the backgroundColor in TopAppBar is not
        // opaque, the elevation is ignored. We need to manually calculate the elevated surface
        // color for dark mode:
        val backgroundColor = Color.Black
        TopAppBar(
            modifier = modifier,
            backgroundColor = backgroundColor.copy(alpha = 0.95f),
            elevation = 0.dp, // No shadow needed
            contentColor = MaterialTheme.colors.onSurface,
            actions = actions,
            title = title,
            navigationIcon = {
                Image(
                    asset = vectorResource(id = R.drawable.arrow_back_24),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .clickable(onClick = onNavIconPressed)
                        .padding(horizontal = 16.dp)
                )
            }
        )
        Divider()
    }
}