package br.com.hivecode.supergames.ui.music

import android.content.Context
import android.net.VpnService.prepare
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.hivecode.supergames.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import okio.Source
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

enum class SourceType {
    local_audio, local_video, http_audio, http_video, playlist;
}

data class PlayerState(var window: Int = 0,
                       var position: Long = 0,
                       var whenReady: Boolean = true,
                       var source: SourceType = SourceType.local_audio
)

class MusicActivity : AppCompatActivity() {

    var playWhenReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        init()
    }

    private fun init() {

    }
}

class PlayerHolder(val context: Context,
                   val playerView: PlayerView,
                   val playerState: PlayerState) : AnkoLogger {
    val player: ExoPlayer

    init {
        // Create the player instance.
        player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
            .also {
                playerView.player = it // Bind to the view.
                info { "SimpleExoPlayer created" }
            }
    }

    fun start() {
        // Load media.
        prepare(buildMediaSource(selectMediaToPlay(state.source)))
        // Start playback when media has buffered enough.
        playWhenReady = true
    }

    fun selectMediaToPlay(source: Source): Uri {
        return when (source) {
            Source.local_audio -> Uri.parse("asset:///audio/file.mp3")
            Source.local_video -> Uri.parse("asset:///video/file.mp4")
            Source.http_audio -> Uri.parse("http://site.../file.mp3")
            Source.http_video -> Uri.parse("http://site.../file.mp4")
        }
    }

    private fun buildMediaSource(uri: Uri): ExtractorMediaSource {
        return ExtractorMediaSource.Factory(
            DefaultDataSourceFactory(ctx, "videoapp")).createMediaSource(uri)
    }

    fun stop() { ... }

    fun release() { ... }
}