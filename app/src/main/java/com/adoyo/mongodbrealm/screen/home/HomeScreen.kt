package com.adoyo.mongodbrealm.screen.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adoyo.mongodbrealm.model.Person
import io.realm.kotlin.types.RealmInstant
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    data: List<Person>,
    filtered: Boolean,
    name: String,
    objectId: String,
    onNameChanged: (String) -> Unit,
    onObjectIdChanged: (String) -> Unit,
    onInsertClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onFilteredClicked: () -> Unit,
    onUpdateClicked: () -> Unit,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    data: List<Person>,
    filtered: Boolean,
    name: String,
    objectId: String,
    onNameChanged: (String) -> Unit,
    onObjectIdChanged: (String) -> Unit,
    onInsertClicked: () -> Unit,
    onUpdateClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onFilterClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Row {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = objectId,
                    onValueChange = onObjectIdChanged,
                    placeholder = { Text(text = "Object Id") })
            }
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = name,
                onValueChange = onNameChanged,
                placeholder = { Text(text = "Name") }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onInsertClicked() }) {
                Text(text = "Add")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Button(onClick = { onUpdateClicked() }) {
                Text(text = "Update")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Button(onClick = { onUpdateClicked() }) {
                Text(text = "Delete")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Button(onClick = { onFilterClicked() }) {
                Text(text = if (filtered) "Clear" else "Filter")
            }
            Spacer(modifier = Modifier.width(6.dp))

        }

    }

}

@Composable
fun PersonView(id: String, name: String, timeStamp: RealmInstant) {
    Row(modifier = Modifier.padding(bottom = 24.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = TextStyle(
                    fontSize =
                    MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            SelectionContainer {
                Text(
                    text = id,
                    style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
                )
            }

        }

    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
        Text(
            text = SimpleDateFormat(
                "hh:mm a",
                Locale.getDefault()
            ).format(Date.from(timeStamp.toInstant())).uppercase(),
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal
            )
        )

    }


}

fun RealmInstant.toInstant(): Instant {
    val sec = this.epochSeconds
    val nano = this.nanosecondsOfSecond
    return if (sec >= 0) {
        Instant.ofEpochSecond(sec, nano.toLong())
    } else {
        Instant.ofEpochSecond(sec - 1, 1_000_000 + nano.toLong())
    }
}
