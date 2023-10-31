package com.example.calculador_de_gorjetas

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculador_de_gorjetas.ui.theme.Calculador_de_gorjetasTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculador_de_gorjetasTheme {
                CalculadoraDeGorjeta()


                }
            }
        }
    }

@Preview(showSystemUi = true)
@Composable
@Preview
fun CalculadoraDeGorjeta(){

    var valorDaConta by remember {mutableStateOf("") }
    var percentagemDeGorjeta by remember {mutableStateOf("") }
    var arredondar by remember { mutableStateOf(false) }

    val percentagemDeGorjetaDouble = percentagemDeGorjeta.toDoubleOrNull()?: 0.0
    val valorDaContaDouble = valorDaConta.toDoubleOrNull()?: 0.0

    val gorjeta = calculadoraDeGorjeta(valorDaContaDouble, percentagemDeGorjetaDouble,arredondar)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Calculadora de Gorjeta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoNumericoEditavel(
            "Valor da Conta",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            valorDaConta,
            onValueChange = { valorDaConta = it}
        )

        CampoNumericoEditavel(
            "% Gorjeta",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            valor = percentagemDeGorjeta,
            onValueChange = { percentagemDeGorjeta = it}
        )

        Spacer(modifier = Modifier.height(16.dp))

        EscolhaArredondamentoGorjeta(
            arredondar = arredondar,
            onMudarArredondamento = { arredondar = it}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.valor_da_gorjeta, gorjeta),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EscolhaArredondamentoGorjeta(
    arredondar: Boolean,
    onMudarArredondamento: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Arredondar Gorjeta")

        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = arredondar,
            onCheckedChange = onMudarArredondamento,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )
        )

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CampoNumericoEditavel(
    label:String,
    keyboardOptions: KeyboardOptions,
    valor: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions
    )
}

private fun calculadoraDeGorjeta(
    valorDaConta:Double,
    percentagemDeGorjeta: Double = 15.0,
    arredondar: Boolean
): String {
    var gorjeta = percentagemDeGorjeta / 100 * valorDaConta
    if(arredondar)
        gorjeta = kotlin.math.ceil(gorjeta)
    return NumberFormat.getCurrencyInstance().format(gorjeta)
}