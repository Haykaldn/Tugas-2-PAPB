package com.example.tugaskalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugaskalkulator.ui.theme.TugasKalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasKalkulatorTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                DisplayScreen(display)
                CalculatorButtons(
                    onButtonClick = { value ->
                        when (value) {
                            "C" -> {
                                display = "0"
                                firstNumber = ""
                                secondNumber = ""
                                operation = ""
                            }
                            "+", "-", "*", "/" -> {
                                if (firstNumber.isNotEmpty()) {
                                    operation = value
                                    display = "$firstNumber $operation"
                                }
                            }
                            "=" -> {
                                if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                                    val result = calculateResult(firstNumber, secondNumber, operation)
                                    display = result
                                    firstNumber = result
                                    secondNumber = ""
                                    operation = ""
                                }
                            }
                            else -> {
                                if (operation.isEmpty()) {
                                    firstNumber += value
                                    display = firstNumber
                                } else {
                                    secondNumber += value
                                    display = "$firstNumber $operation $secondNumber"
                                }
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))  // Jarak antara tombol dan identitas
                IdentitySection()
            }
        }
    )
}

@Composable
fun DisplayScreen(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFF1C1C1C), RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = text,
            fontSize = 56.sp,
            color = Color.Green,
            maxLines = 1,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CalculatorButtons(onButtonClick: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val buttons = listOf(
            listOf("C", "/", "*", "-"),
            listOf("7", "8", "9", "+"),
            listOf("4", "5", "6", "="),
            listOf("1", "2", "3", "0")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (button in row) {
                    CalculatorButton(
                        text = button,
                        onClick = { onButtonClick(button) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (text in listOf("+", "-", "*", "/")) Color.Green else Color(0xFF2C2C2C)
    )
    val gradientBrush = Brush.linearGradient(
        listOf(Color(0xFF00FF6A), Color(0xFF006400))
    )

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(brush = gradientBrush, shape = RoundedCornerShape(12.dp))
            .shadow(12.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 36.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IdentitySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Haykal Dani Muhammad",
            fontSize = 20.sp,
            color = Color.Green,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "NIM: 225150607111031",
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

fun calculateResult(firstNumber: String, secondNumber: String, operation: String): String {
    val num1 = firstNumber.toDoubleOrNull() ?: return "Error"
    val num2 = secondNumber.toDoubleOrNull() ?: return "Error"

    return when (operation) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "*" -> (num1 * num2).toString()
        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
        else -> "Error"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TugasKalkulatorTheme {
        CalculatorApp()
    }
}
