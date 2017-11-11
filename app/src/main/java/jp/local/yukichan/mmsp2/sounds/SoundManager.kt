package jp.local.yukichan.mmsp2.sounds

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import jp.local.yukichan.mmsp2.notes.Note
import jp.local.yukichan.mmsp2.notes.NoteConstants
import jp.local.yukichan.mmsp2.notes.NoteInfo
import jp.local.yukichan.mmsp2.notes.NoteManager
import timber.log.Timber

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
class SoundManager(val context: Context, private val noteManager: NoteManager) {

    private val LOGTAG: String = SoundManager::class.java.simpleName

    private val soundPool: SoundPool

    private val soundMap: Map<Note, Int>

    init {
        val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(16)
                .build();
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            Timber.d("soundPool=$soundPool sampleId=$sampleId status=$status")
        }

        soundMap = mutableMapOf()
        NoteInfo.values().filter { it.type != NoteInfo.Type.Flat }.forEach { noteInfo ->
            for (octave in 0..NoteConstants.MAX_OCTAVE) {
                val note = noteManager.get(noteInfo, octave)
                Timber.d("DEBUG: note=$note filePath=${note.filePath}")
                val descriptor = context.assets.openFd(note.filePath) ?: continue
                soundMap[note] = soundPool.load(descriptor, 1)
            }
        }
    }

    fun play(note: Note?, leftVolume: Float = 1f, rightVolume: Float = 1f) {
        soundPool.play(soundMap[note]!!, 1f, 1f, 1, 0, 0f)
    }
}