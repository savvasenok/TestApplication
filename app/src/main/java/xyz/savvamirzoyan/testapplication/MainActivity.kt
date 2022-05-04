package xyz.savvamirzoyan.testapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_request_permission).setOnClickListener {
            tryActionWithPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                R.string.reason_need_permission,
            ) {
                actionWithPermission()
            }
        }

        findViewById<Button>(R.id.button_open_settings).setOnClickListener {
            openAppSettings()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
        }
        startActivity(intent)
    }

    private val requestSpecificPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            actionWithPermission()
        } else {
            Toast.makeText(this, "Launched permission not granted :(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actionWithPermission() {
        Toast.makeText(this, "Action with granted permission", Toast.LENGTH_SHORT).show()
    }

    private fun hasPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

    private fun showAlertDialog(@StringRes message: Int, listener: (() -> Unit)? = null): AlertDialog =
        MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> listener?.invoke() }
            .show()

    private fun requestPermission(
        permission: String
    ) {
        requestSpecificPermissionLauncher.launch(permission)
    }

    private fun tryActionWithPermission(
        permission: String,
        @StringRes reasonNeedPermission: Int,
        actionGranted: () -> Unit
    ) {
        when {
            hasPermission(permission) -> actionGranted()
            shouldShowRequestPermissionRationale(permission) -> showAlertDialog(reasonNeedPermission) {
                requestPermission(permission)
            }
            else -> requestPermission(permission)
        }
    }
}