package com.kapirti.zikirmatik

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kapirti.zikirmatik.databinding.ActivityMainBinding
import java.util.*
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        loadLanguage()

        val navController=findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.top_menu_lang,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menuTopLang){
            showLanguageDialog()
        }
        else if (item.itemId==R.id.share){
            share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLanguageDialog(){
        val listItem=arrayOf("English","Turkish")
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Choose lang")
        builder.setItems(listItem){dialog, which->
            when(which){
                0->{
                    setLocale("en")
                    recreate()
                }
                1->{
                    setLocale("tr")
                    recreate()
                }
            }
            dialog.dismiss()
        }
        val mDialog:AlertDialog=builder.create()
        mDialog.show()
    }
    private fun setLocale(lang:String){
        val locale= Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration =Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        val editor:SharedPreferences.Editor=getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang",lang)
        editor.apply()
    }
    private fun loadLanguage(){
        val pref:SharedPreferences=getSharedPreferences("Setting", Activity.MODE_PRIVATE)
        val language:String=pref.getString("My_Lang","").toString()
        setLocale(language)
    }

    private fun share(){
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,R.string.share_text)
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent, R.string.share_to.toString()))
    }

}