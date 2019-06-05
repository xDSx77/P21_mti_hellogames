package fr.epita.android.hellogames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {
    var data : List<Games>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service : WebServiceAPI = retrofit.create(WebServiceAPI::class.java)
        val wsCallback = object : Callback<List<Games>> {
            override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                Log.w("WebService", "WS Error: $t")
            }

            override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                if (response.code() == 200) {
                    data = response.body()!!.shuffled().take(4)
                    Glide
                        .with(this@MainFragment)
                        .load(data!![0].picture)
                        .into(Game1)
                    Glide
                        .with(this@MainFragment)
                        .load(data!![1].picture)
                        .into(Game2)
                    Glide
                        .with(this@MainFragment)
                        .load(data!![2].picture)
                        .into(Game3)
                    Glide
                        .with(this@MainFragment)
                        .load(data!![3].picture)
                        .into(Game4)
                }
            }
        }
        service.listGames().enqueue(wsCallback)

        val game1TextView : AppCompatImageView = view.findViewById(R.id.Game1)
        game1TextView.setOnClickListener {
            (activity as MainActivity).GameClicked(data!![0])
        }

        val game2TextView : AppCompatImageView = view.findViewById(R.id.Game2)
        game2TextView.setOnClickListener {
            (activity as MainActivity).GameClicked(data!![1])
        }

        val game3TextView : AppCompatImageView = view.findViewById(R.id.Game3)
        game3TextView.setOnClickListener {
            (activity as MainActivity).GameClicked(data!![2])
        }

        val game4TextView : AppCompatImageView = view.findViewById(R.id.Game4)
        game4TextView.setOnClickListener {
            (activity as MainActivity).GameClicked(data!![3])
        }
    }


}
