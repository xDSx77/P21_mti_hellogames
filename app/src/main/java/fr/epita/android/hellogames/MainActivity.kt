package fr.epita.android.hellogames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), InteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, MainFragment())
            .commit()
    }

    override fun GameClicked(game : Games) {
        val gameBundle = Bundle()
        gameBundle.putInt("id", game.id)
        val gameFragment = GameFragment()
        gameFragment.arguments = gameBundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, gameFragment)
            .addToBackStack(null)
            .commit()
    }
}
