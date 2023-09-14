package com.example.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val respuestaLoginAuthUi=registerForActivityResult(FirebaseAuthUIActivityResultContract()){
            res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode=== RESULT_OK){
            if(res.idpResponse !=null){
                //Logica de negocio
                seLogeo(res.idpResponse!!)
            }
        }
    }

    //@SuppressLint("RestrictedApi")  //para q funciones el res.user.name
    fun seLogeo(res: IdpResponse){
        val btnLogin:Button=findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout=findViewById<Button>(R.id.btn_logout_firebase)

        //
        val tvBienvenido=findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text= FirebaseAuth.getInstance().currentUser?.displayName
        btnLogout.visibility= View.VISIBLE
        btnLogin.visibility= View.INVISIBLE

        if(res.isNewUser==true){
            registrarUsuarioPorPrimeraVez(res)
        }

        // Abrir MainActivity después de iniciar sesión
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){
        /* usuario.email;  //luego auiq sigue la logica de enviar un correo al logearse, por ejm
         usuario.phoneNumber;
         usuario.user.name;*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val btnLogin=findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers= arrayListOf(
                //Arreglo de PROVIDERS para logearse
                //Ej: Correo, FB, TW, google
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            //Construimos el intent de login
            val logearseIntent=
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build()

            //Respuesta del intent de Login
            respuestaLoginAuthUi.launch(logearseIntent)


        }
        val btnLogout=findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener { seDeslogeo() }

        //usuarioactual
        val usuario= FirebaseAuth.getInstance().currentUser
        if(usuario !=null){
            val tvBienvenido=findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin=findViewById<Button>(R.id.btn_login_firebase)
            val btnLogout=findViewById<Button>(R.id.btn_logout_firebase)
            tvBienvenido.text=usuario.displayName
            btnLogout.visibility= View.VISIBLE
            btnLogin.visibility= View.INVISIBLE
        }
    }

    fun seDeslogeo(){
        val btnLogin=findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout=findViewById<Button>(R.id.btn_logout_firebase)

        val tvBienvenido=findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text="MOON's"
        btnLogout.visibility= View.INVISIBLE
        btnLogin.visibility= View.VISIBLE

        FirebaseAuth.getInstance().signOut()  //esta instancia sirve para tomar los datos del user actual, idk

    }



}