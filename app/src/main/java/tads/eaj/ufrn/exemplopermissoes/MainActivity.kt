package tads.eaj.ufrn.exemplopermissoes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.provider.MediaStore
import android.net.Uri
import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.CALL_PHONE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import android.graphics.Bitmap
import android.widget.ImageView




class MainActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        PermissionUtil.validate(this,255, CALL_PHONE, READ_CONTACTS)
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = arrayOf("Ligar para telefone", "Discar para telefone",
            "Enviar E-mail", "Enviar SMS", "Abrir Browser",
            "Mapa - Lat/Lng", "Mapa - Endereco", "Mapa - Rota",
            "Compartilhar",
            "Camera Foto", "Camera Vídeo",
            "Intent customizada", "Browser customizado",
            "Sair")
        listview.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        listview.setOnItemClickListener{ adapterView: AdapterView<*>, view: View, position: Int, l: Long ->
            when (position) {
                0 -> {
                    var uri = Uri.parse("tel:(84)98888-1234")
                    var intent = Intent(Intent.ACTION_CALL, uri)
                    startActivity(intent)
                }
                1 -> {
                    var uri = Uri.parse("tel:(84)98888-1234")
                    var intent = Intent(Intent.ACTION_DIAL, uri)
                    startActivity(intent)
                }
                2 -> {
                    // Email
                    var emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Título do Email")
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Mensagem do Email")
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, "tanirocr@gmail.com")
                    emailIntent.type = "message/rfc822"
                    startActivity(emailIntent)
                }
                3 -> {
                    // SMS
                    var uri = Uri.parse("sms:(84)98888-1234")
                    var smsIntent = Intent(Intent.ACTION_SENDTO, uri)
                    smsIntent.putExtra("sms_body", "Olá, isso é uma mensagem :)")
                    startActivity(smsIntent)
                }
                4 -> {
                    // Browser
                    var uri = Uri.parse("http://google.com")
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                5 -> {
                    // Mapa
                    var GEO_URI = "geo:-5.8841826,-35.365046?q=(EAJ)"
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(GEO_URI))
                    startActivity(intent)
                }
                6 -> {
                    // Mapa
                    var GEO_URI = "geo:0,0?q=UFRN"
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(GEO_URI))
                    startActivity(intent)
                }
                7 -> {
                    // Rota
                    var rota =
                        "http://maps.google.com/maps?saddr=-5.8841826,-35.365046&daddr=-5.8579104,-35.3558422"
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(rota))
                    startActivity(intent)
                }
                8 -> {
                    // Compartilhar
                    var shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar")
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Bla bla bla")
                    startActivity(shareIntent)
                }
                9 -> {
                    // Tirar foto
                    // "android.media.action.IMAGE_CAPTURE
                    var fotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(fotoIntent, 9)
                }
                10 -> {
                    // Gravar Vídeo
                    // android.media.action.VIDEO_CAPTURE
                    var videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    startActivityForResult(videoIntent, 0)
                }
                11 -> {
                    // INTENT_FILTER
                    var intent = Intent("tads.eaj.com.intentexampleb.TESTE")
                    startActivity(intent)
                }
                12 -> {
                    // INTENT_FILTER
                    var uri = Uri.parse("http://tads.eaj.ufrn.br")
                    var intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                else -> finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, agora é com você :-)
                PermissionUtil.alertAndFinish(this, R.string.app_name)
                return
            }
        }
        // Se chegou aqui está OK :-)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
            val bundle = data?.getExtras()
            if (bundle != null) {
                // Recupera o Bitmap retornado pela câmera
                val bitmap = bundle!!.get("data") as Bitmap
                showToastImageView(bitmap)
            }
        }
    }

    private fun showToastImageView(bitmap: Bitmap) {
        val t = Toast(this)
        val imgView = ImageView(this)
        imgView.setImageBitmap(bitmap)
        t.view = imgView
        t.show()
    }
}
