package com.project.apptest.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.project.apptest.R
import com.project.apptest.model.data.Post
import kotlinx.serialization.json.JsonDecodingException
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException


class MainActivity : AppCompatActivity(R.layout.main_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        var post = Post()
        title = getString(R.string.title)

        findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
        try {
            post = postViewModel.getFirstPost()
        } catch (e: JsonDecodingException) {
            Toast.makeText(
                this,
                "Ошибка декодирования полученных данных: " + e.message,
                Toast.LENGTH_LONG
            ).show()
        } catch (e: SocketTimeoutException) {
            Toast.makeText(this, "Сервер недоступен: " + e.message, Toast.LENGTH_LONG).show()
        } catch (e: HttpException) {
            Toast.makeText(this, "Неожиданный ответ сервера: " + e.message, Toast.LENGTH_LONG)
                .show()
        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка: " + e.message, Toast.LENGTH_LONG).show()
        }

        loadGIF(post, this)
        findViewById<TextView>(R.id.description_post).text = post.description
        findViewById<ImageButton>(R.id.button_previous_gif).isEnabled = false
        findViewById<ImageButton>(R.id.button_previous_gif).background =
            getDrawable(R.drawable.round_unbutton_background)

        postViewModel.changeStateBackButton().observe(this, Observer {
            findViewById<ImageButton>(R.id.button_previous_gif).isEnabled = it.boolean
            if (it.boolean) {
                findViewById<ImageButton>(R.id.button_previous_gif).background =
                    getDrawable(R.drawable.round_button_background)
            } else {
                findViewById<ImageButton>(R.id.button_previous_gif).background =
                    getDrawable(R.drawable.round_unbutton_background)
            }
        })

        findViewById<ImageButton>(R.id.button_previous_gif).setOnClickListener {
            findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
            try {
                post = postViewModel.getPrevPost()
                findViewById<TextView>(R.id.description_post).text = post.description
            } catch (e: JsonDecodingException) {
                Toast.makeText(
                    this,
                    "Ошибка декодирования полученных данных: " + e.message,
                    Toast.LENGTH_LONG
                )
                    .show()
            } catch (e: SocketTimeoutException) {
                Toast.makeText(this, "Сервер недоступен: " + e.message, Toast.LENGTH_LONG).show()
            } catch (e: HttpException) {
                Toast.makeText(this, "Неожиданный ответ сервера: " + e.message, Toast.LENGTH_LONG)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка: " + e.message, Toast.LENGTH_LONG).show()
            }
            loadGIF(post, this)
        }

        findViewById<ImageButton>(R.id.button_next_gif).setOnClickListener {
            findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
            try {
                post = postViewModel.getNextPost()
                findViewById<TextView>(R.id.description_post).text = post.description
            } catch (e: JsonDecodingException) {
                Toast.makeText(
                    this,
                    "Ошибка декодирования полученных данных: " + e.message,
                    Toast.LENGTH_LONG
                )
                    .show()
            } catch (e: SocketTimeoutException) {
                Toast.makeText(this, "Сервер недоступен: " + e.message, Toast.LENGTH_LONG).show()
            } catch (e: HttpException) {
                Toast.makeText(this, "Неожиданный ответ сервера: " + e.message, Toast.LENGTH_LONG)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка: " + e.message, Toast.LENGTH_LONG).show()
            }
            loadGIF(post, this)
        }
    }

    private fun loadGIF(post: Post, context: Context) {
        Glide.with(this).asGif()
            .load(post.gifURL).error(R.drawable.ic_baseline_error_24).listener(object :
                RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(context, "Ошибка загрузки: " + e?.message, Toast.LENGTH_LONG)
                        .show()
                    findViewById<ProgressBar>(R.id.progress).visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    findViewById<ProgressBar>(R.id.progress).visibility = View.INVISIBLE
                    return false
                }

            })
            .into(findViewById(R.id.view_gif))
    }
}