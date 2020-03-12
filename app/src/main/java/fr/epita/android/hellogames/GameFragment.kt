package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_game.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameFragment : Fragment() {
    var data : GameDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments!!.getInt("id")
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service : GameDetailAPI = retrofit.create(GameDetailAPI::class.java)
        val wsCallback = object : Callback<GameDetail> {
            override fun onFailure(call: Call<GameDetail>, t: Throwable) {
                Log.w("WebService", "WS Error: $t")
            }

            override fun onResponse(call: Call<GameDetail>, response: Response<GameDetail>) {
                if (response.code() == 200) {
                    data = response.body()
                    Glide
                        .with(this@GameFragment)
                        .load(data!!.picture)
                        .into(GameImageView)
                    NameValueView.text = data!!.name
                    TypeValueView.text = data!!.type
                    NbPlayersValueView.text = data!!.players.toString()
                    YearValueView.text = data!!.year.toString()
                    DescriptionView.text = data!!.description_en
                }
                MoreInfoButton.setOnClickListener {
                    val url = data!!.url
                    val implicitIntent = Intent(Intent.ACTION_VIEW)
                    implicitIntent.data = Uri.parse(url)
                    startActivity(implicitIntent)
                }
            }
        }
        service.listGameDetail(id).enqueue(wsCallback)
    }

}
