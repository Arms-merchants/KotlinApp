package p.diqiugang.foriseinvest.com.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by heyueyang on 2017/6/27.
 */
class TestActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        ButterKnife.bind(this)
        seekbar1.setOnSeekBarChangeListener(this)
        seekbar2.setOnSeekBarChangeListener(this)
        seekbar3.setOnSeekBarChangeListener(this)
        seekbar4.setOnSeekBarChangeListener(this)
    }


    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        handleProgress(seekBar, progress)
    }

    private fun handleProgress(seekBar: SeekBar?, progress: Int) {
        when (seekBar?.id) {
            R.id.seekbar1 -> {

            }

        }
    }

}