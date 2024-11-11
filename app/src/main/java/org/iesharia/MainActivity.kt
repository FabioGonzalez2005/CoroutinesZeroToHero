package org.iesharia.coroutineszerotohero

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import org.iesharia.coroutineszerotohero.ui.theme.CoroutinesZeroToHeroTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var comments by mutableStateOf<List<Comment>>(emptyList())
        lifecycleScope.launch(Dispatchers.IO) {
            val resultado: Response<List<Comment>> = RetrofitHelper.apiService.getComments()
            withContext(Dispatchers.Main) {
                if (resultado.isSuccessful) {
                    Log.i("ejemplo", "Respuesta de API exitosa")
                    comments = resultado.body() ?: emptyList()

                    Toast.makeText(this@MainActivity, "Funciona", Toast.LENGTH_SHORT).show()
                } else {
                    Log.i("ejemplo", "Error en la respuesta: ${resultado.errorBody()}")
                }
            }
        }

        setContent {
            CoroutinesZeroToHeroTheme {
                Surface {
                    CommentList(comments = comments)
                }
            }
        }
    }
}

@Composable
fun CommentList(comments: List<Comment>) {
    LazyColumn {
        items(comments) { comment ->
            CommentItem(comment)
        }
    }
}
@Composable
fun CommentItem(comment: Comment) {
    Column {
        Text(text = "ID: ${comment.id}")
        Text(text = "Post ID: ${comment.postId}")
        Text(text = "Nombre: ${comment.name}")
        Text(text = "Correo: ${comment.email}")
        Text(text = "Cuerpo: ${comment.body}")
        Text(text = "--------------------------")
    }
}
