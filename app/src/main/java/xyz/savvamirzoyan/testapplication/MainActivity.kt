package xyz.savvamirzoyan.testapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressButton = findViewById<ProgressButton>(R.id.buttonTestLoading).apply {
            setOnClickListener { this.startLoading() }
        }

        findViewById<Button>(R.id.buttonStopLoading).apply {
            setOnClickListener { progressButton.stopLoading() }
        }
    }
}