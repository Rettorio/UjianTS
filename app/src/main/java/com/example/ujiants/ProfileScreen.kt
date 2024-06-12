package com.example.ujiants

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ujiants.navigation.LocalNavController

data class IdentityRow(val label: String, val body: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
) {
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigation icon")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        ProfileScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
fun IdentityDrawer(
   modifier: Modifier = Modifier,
   identityList: List<IdentityRow>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(identityList) {(label, body, icon) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.tertiary)
                OutlinedTextField(
                    value = body,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(text = label) },
                    textStyle = MaterialTheme.typography.labelLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledLabelColor = MaterialTheme.colorScheme.secondary,
                        disabledTextColor = MaterialTheme.colorScheme.secondary,
                        disabledBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

    }
}

@Composable
fun ProfileScreenContent(modifier: Modifier = Modifier) {
    val myBio: List<IdentityRow> = listOf(
        IdentityRow(label = "Name", body = "Ardiansyah Putraman Rukua", icon = Icons.Outlined.AccountBox),
        IdentityRow(label = "Tanggal Lahir", body = "19 Juli 2004", icon = Icons.Outlined.Event),
        IdentityRow(label = "No Hp", body = "081249498349", icon = Icons.Outlined.Smartphone),
        IdentityRow(label = "Kampus", body = "ITB Stikom Ambon", icon = Icons.Outlined.HomeWork),
        IdentityRow(label = "Prodi & Kelas", body = "Teknik Informatika - TI4D", icon = Icons.Outlined.School),
        IdentityRow(label = "Nim", body = "220101115", icon = Icons.Default.PermIdentity)
    )

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pas),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .width(250.dp)
                .clip(MaterialTheme.shapes.large),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )
        Spacer(modifier = Modifier.height(30.dp))
        IdentityDrawer(identityList = myBio)
    }
}