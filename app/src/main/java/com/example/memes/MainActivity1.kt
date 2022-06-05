package com.example.memes

import android.app.Notification
import android.content.Intent
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main1.*

class MainActivity1 : AppCompatActivity() {

    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        loadmeme()
    }

    private fun loadmeme()
    {
        ProgressBar.visibility=View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl=response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar .visibility=View.GONE
                        return false
                    }
                }).into(memeImage)

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            })

        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)

    }

    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this cool meme $currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share this meme....")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {
        loadmeme()
    }
}
