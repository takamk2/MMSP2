package jp.local.yukichan.mmsp2.notes.scale

import jp.local.yukichan.mmsp2.notes.NoteConstants
import jp.local.yukichan.mmsp2.notes.NoteManager
import jp.local.yukichan.mmsp2.notes.getKeysOfScale
import jp.local.yukichan.mmsp2.notes.scale.ScaleConstitution.*
import jp.local.yukichan.mmsp2.sequence.SequenceItem

/**
 * Created by takamk2 on 17/10/18.
 *
 * The Edit Fragment of Base Class.
 */
class ScaleManager(private val noteManager: NoteManager, private val octave: Int = 0) {

    private val scales = mutableListOf<Scale>()

    init {
        ScaleConstitution.values()
                .filter { it != All }
                .forEach {
                    getKeysOfScale().mapTo(scales) { noteInfo ->
                        Scale(noteManager, noteManager.get(noteInfo, octave), it)
                    }
                }
    }

    fun getScale(): Scale {
        return scales[0]
    }

    fun getCandidateScales(seqItem: SequenceItem): List<Scale> {
        return scales.filter { scale ->
            scale.constitution.intervalSet
                    .map {
                        (it.noteNo + scale.rootNote.noteNo + NoteConstants.INTERVAL) %
                                NoteConstants.INTERVAL
                    }
                    .toSet().containsAll(seqItem.notes.map { it.noteNo })
        }.toList()
    }

    fun getCandidateScales(internalNoteNoSet: Set<Int>): List<Scale> {
        return scales.filter { scale ->
            scale.constitution.intervalSet
                    .map {
                        (it.noteNo + scale.rootNote.noteNo + NoteConstants.INTERVAL) %
                                NoteConstants.INTERVAL
                    }
                    .toSet().containsAll(internalNoteNoSet)
        }.toList()
    }
}