package br.edu.ifsp.scl.prdm.sc305540x.trucoscoreboardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrucoScoreBoard()
                }
            }
        }
    }
}

@Composable
fun TrucoScoreBoard() {

    var pontosA by remember {
        mutableIntStateOf(0)
    }

    var pontosB by remember {
        mutableIntStateOf(0)
    }

    var partidaFinalizada by remember {
        mutableStateOf(false)
    }

    var mostrarMaoDeOnze by remember {
        mutableStateOf(false)
    }

    var equipeMaoDeOnze by remember {
        mutableStateOf("")
    }

    var mostrarVencedor by remember {
        mutableStateOf(false)
    }

    var equipeVencedora by remember {
        mutableStateOf("")
    }

    fun adicionarPontos(
        equipe: String,
        pontos: Int
    ) {

        if (partidaFinalizada) {
            return
        }

        if (equipe == "A") {
            pontosA += pontos
        } else {
            pontosB += pontos
        }

        // Verifica vitória
        if (pontosA >= 12) {

            equipeVencedora = "Equipe A"
            partidaFinalizada = true
            mostrarVencedor = true

            return
        }

        if (pontosB >= 12) {

            equipeVencedora = "Equipe B"
            partidaFinalizada = true
            mostrarVencedor = true

            return
        }

        // Verifica mão de 11
        if (pontosA == 11) {

            equipeMaoDeOnze = "Equipe A"
            mostrarMaoDeOnze = true

            return
        }

        if (pontosB == 11) {

            equipeMaoDeOnze = "Equipe B"
            mostrarMaoDeOnze = true

            return
        }
    }

    fun reiniciar() {

        pontosA = 0
        pontosB = 0

        partidaFinalizada = false

        mostrarMaoDeOnze = false
        mostrarVencedor = false

        equipeMaoDeOnze = ""
        equipeVencedora = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "TRUCO SCORE BOARD",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            EquipeCard(
                nome = "EQUIPE A",
                pontos = pontosA,
                cor = Color(0xFF1565C0),
                onMaisUm = {
                    adicionarPontos("A", 1)
                },
                onMaisTres = {
                    adicionarPontos("A", 3)
                },
                modifier = Modifier.weight(1f),
                habilitado = !partidaFinalizada
            )

            EquipeCard(
                nome = "EQUIPE B",
                pontos = pontosB,
                cor = Color(0xFFC62828),
                onMaisUm = {
                    adicionarPontos("B", 1)
                },
                onMaisTres = {
                    adicionarPontos("B", 3)
                },
                modifier = Modifier.weight(1f),
                habilitado = !partidaFinalizada
            )
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Equipe A: $pontosA pontos  •  Equipe B: $pontosB pontos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                reiniciar()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "REINICIAR PARTIDA"
            )
        }
    }

    // Dialog da mão de 11

    if (mostrarMaoDeOnze) {

        AlertDialog(
            onDismissRequest = {
                mostrarMaoDeOnze = false
            },
            title = {
                Text(
                    text = "Mão de 11"
                )
            },
            text = {
                Text(
                    text = "$equipeMaoDeOnze entrou na mão de 11 pontos!"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarMaoDeOnze = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    // Dialog do vencedor

    if (mostrarVencedor) {

        AlertDialog(
            onDismissRequest = {
                mostrarVencedor = false
            },
            title = {
                Text(
                    text = "Fim da partida"
                )
            },
            text = {
                Text(
                    text = "$equipeVencedora venceu o jogo!"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarVencedor = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun EquipeCard(
    nome: String,
    pontos: Int,
    cor: Color,
    onMaisUm: () -> Unit,
    onMaisTres: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true
) {

    Card(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = nome,
                color = cor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = pontos.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = onMaisUm,
                enabled = habilitado,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+1 PONTO")
            }

            Button(
                onClick = onMaisTres,
                enabled = habilitado,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+3 PONTOS")
            }
        }
    }
}