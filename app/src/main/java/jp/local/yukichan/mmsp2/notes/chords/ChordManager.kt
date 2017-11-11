package jp.local.yukichan.mmsp2.notes.chords

import jp.local.yukichan.mmsp2.notes.NoteInfo
import jp.local.yukichan.mmsp2.notes.NoteManager
import jp.local.yukichan.mmsp2.notes.convertToNoteInfo
import jp.local.yukichan.mmsp2.notes.getAppendedNoteNo
import jp.local.yukichan.mmsp2.notes.scale.ScaleConstitution
import timber.log.Timber

/**
 * Created by takamk2 on 17/10/19.
 *
 * The Edit Fragment of Base Class.
 */
class ChordManager(private val noteManager: NoteManager) {

    private val chordList = mutableListOf<Chord>()

    init {
        val octave = 2
//        Note.Name.values().forEach { noteName ->
//            Log.d(javaClass.simpleName, "1 noteName=$noteName")
//            MajorScaleIntervals.values().forEach { intervals ->
//                Log.d(javaClass.simpleName, "2 intervals=$intervals")
//                val noteNo = noteName.noteId + intervals.intervalFromRoot
//                intervals.codeConstitutions.forEach { codeConstitution ->
//                    Log.d(javaClass.simpleName, "3 codeConstitution=$codeConstitution")
//                    chordList.add(Chord(noteManager, Note(noteNo, octave), codeConstitution))
//                }
//            }
//        }
//        Log.d(javaClass.simpleName, "4 size=${chordList.size}")
//        Log.d(javaClass.simpleName, "4 chordList=$chordList")
        chordList.add(Chord(noteManager, noteManager.get(0, octave), ChordConstitution.MajorSeventh))
    }

    fun getCode(rootNoteNo: Int, constitution: ChordConstitution, octave: Int): Chord {
        val code = Chord(noteManager, noteManager.get(rootNoteNo, octave), constitution)
        Timber.d("DEBUG: getCode(rootNoteNo=$rootNoteNo constitution=$constitution octave=$octave) code=$code")
        return code
    }

    fun getCode(rootNoteInfo: NoteInfo, constitution: ChordConstitution, octave: Int): Chord {
        val code = Chord(noteManager, noteManager.get(rootNoteInfo, octave), constitution)
        Timber.d("DEBUG: getCode(rootNoteInfo=$rootNoteInfo constitution=$constitution octave=$octave) code=$code")
        return code
    }

    fun getCandidateCodes(rootNoteInfo: NoteInfo): List<Chord> {
        val codes = mutableListOf<Chord>()
        ScaleConstitution.MajorScale.intervalSet.forEach { interval ->
            val octave = 2
            val intervalList = listOf(interval,
                    ScaleConstitution.MajorScale.getRelativePositionInterval(interval, 2),
                    ScaleConstitution.MajorScale.getRelativePositionInterval(interval, 4),
                    ScaleConstitution.MajorScale.getRelativePositionInterval(interval, 6))
            val constitution = convertToConstitution(intervalList)
            val noteNo = getAppendedNoteNo(rootNoteInfo.noteNo, interval.noteNo)
            val noteInfo = convertToNoteInfo(noteNo, rootNoteInfo.type)
            val code = if (noteInfo != null) {
                getCode(noteInfo, constitution, octave)
            } else {
                getCode(noteNo, constitution, octave)
            }
            codes.add(code)
        }
        return codes
    }

    fun getCandidateCodes(rootNoteNo: Int): List<Chord> {
        val codes = mutableListOf<Chord>()
        ScaleConstitution.MajorScale.intervalSet.forEach { interval ->
            val octave = 2
            val intervalList = listOf(interval,
                    ScaleConstitution.MajorScale.getRelativePositionInterval(interval, 2),
                    ScaleConstitution.MajorScale.getRelativePositionInterval(interval, 4),
                    ScaleConstitution.MajorScale.getRelativePositionInterval(interval, 6))
            val constitution = convertToConstitution(intervalList)
            val size = ScaleConstitution.All.intervalSet.size
            val no = (interval.noteNo + rootNoteNo + size) % size
            val code = getCode(no, constitution, octave)
            codes.add(code)
        }
        return codes
    }
}