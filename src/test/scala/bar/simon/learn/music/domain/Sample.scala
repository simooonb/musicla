package bar.simon.learn.music.domain

import bar.simon.learn.music.domain.answers.Answer.{IntervalBetweenNotesAnswer, ScaleHarmonizationAnswer}
import bar.simon.learn.music.domain.answers.{Answer, AnswerVerification}
import bar.simon.learn.music.domain.music.Alteration._
import bar.simon.learn.music.domain.music.Interval._
import bar.simon.learn.music.domain.music.{Chord, Note, NoteName, Scale}
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question._

// TODO: split into multiple sample files
object Sample {
  val A: Note           = Note(NoteName.A)
  val ASharp: Note      = Note(NoteName.A, Some(Sharp))
  val AFlat: Note       = Note(NoteName.A, Some(Flat))
  val B: Note           = Note(NoteName.B)
  val BSharp: Note      = Note(NoteName.B, Some(Sharp))
  val BFlatFlat: Note   = Note(NoteName.B, Some(FlatFlat))
  val BFlat: Note       = Note(NoteName.B, Some(Flat))
  val C: Note           = Note(NoteName.C)
  val CSharp: Note      = Note(NoteName.C, Some(Sharp))
  val CSharpSharp: Note = Note(NoteName.C, Some(SharpSharp))
  val CFlat: Note       = Note(NoteName.C, Some(Flat))
  val D: Note           = Note(NoteName.D)
  val DSharp: Note      = Note(NoteName.D, Some(Sharp))
  val DFlat: Note       = Note(NoteName.D, Some(Flat))
  val E: Note           = Note(NoteName.E)
  val ESharp: Note      = Note(NoteName.E, Some(Sharp))
  val EFlat: Note       = Note(NoteName.E, Some(Flat))
  val F: Note           = Note(NoteName.F)
  val FSharp: Note      = Note(NoteName.F, Some(Sharp))
  val FSharpSharp: Note = Note(NoteName.F, Some(SharpSharp))
  val FFlat: Note       = Note(NoteName.F, Some(Flat))
  val G: Note           = Note(NoteName.G)
  val GSharp: Note      = Note(NoteName.G, Some(Sharp))
  val GSharpSharp: Note = Note(NoteName.G, Some(SharpSharp))
  val GFlat: Note       = Note(NoteName.G, Some(Flat))

  val CMaj: Chord  = Chord.Major(C)
  val DMin: Chord  = Chord.Minor(D)
  val EMin: Chord  = Chord.Minor(E)
  val FMaj: Chord  = Chord.Major(F)
  val GMaj: Chord  = Chord.Major(G)
  val AMin: Chord  = Chord.Minor(A)
  val AMin2: Chord = Chord.Minor(A.copy(octave = 2))
  val BDim: Chord  = Chord.Diminished(B)
  val BDim2: Chord = Chord.Diminished(B.copy(octave = 2))

  val intervals = List(
    (A, unison, A),
    (A, minorSecond, BFlat),
    (A, majorSecond, B),
    (A, minorThird, C),
    (A, majorThird, CSharp),
    (A, fourth, D),
    (A, sharpFourth, DSharp),
    (A, flatFifth, EFlat),
    (A, fifth, E),
    (A, minorSixth, F),
    (A, majorSixth, FSharp),
    (A, minorSeventh, G),
    (A, majorSeventh, GSharp),
    (EFlat, unison, EFlat),
    (EFlat, minorSecond, FFlat),
    (EFlat, majorSecond, F),
    (EFlat, minorThird, GFlat),
    (EFlat, majorThird, G),
    (EFlat, fourth, AFlat.copy(octave = 2)),
    (EFlat, sharpFourth, A.copy(octave = 2)),
    (EFlat, flatFifth, BFlatFlat.copy(octave = 2)),
    (EFlat, fifth, BFlat.copy(octave = 2)),
    (EFlat, minorSixth, CFlat.copy(octave = 2)),
    (EFlat, majorSixth, C.copy(octave = 2)),
    (EFlat, minorSeventh, DFlat.copy(octave = 2)),
    (EFlat, majorSeventh, D.copy(octave = 2)),
    (B, unison, B),
    (B, minorSecond, C),
    (B, majorSecond, CSharp),
    (B, minorThird, D),
    (B, majorThird, DSharp),
    (B, fourth, E),
    (B, sharpFourth, ESharp),
    (B, flatFifth, F),
    (B, fifth, FSharp),
    (B, minorSixth, G),
    (B, majorSixth, GSharp),
    (B, minorSeventh, A.copy(octave = 2)),
    (B, majorSeventh, ASharp.copy(octave = 2))
  )

  // C D E F G A B
  val majorC: Scale = Scale.Major(C)
  // A# B# C## D# E# F## G##
  val majorASharp: Scale = Scale.Major(ASharp)
  // Eb F G Ab Bb C D
  val majorEFlat: Scale = Scale.Major(EFlat)
  // Fb Gb Ab Bbb Cb Db Eb
  val majorFFlat: Scale = Scale.Major(FFlat)

  // A B C D E F G
  val minorA: Scale = Scale.Minor(A)
  // E F# G A B C D
  val minorE: Scale = Scale.Minor(E)
  // D# E# F# G# A# B C#
  val minorDSharp: Scale = Scale.Minor(DSharp)
  // Bb C Db Eb F Gb Ab
  val minorBFlat: Scale = Scale.Minor(BFlat)

  val scaleNotesQuestion: Question         = ScaleNotes(minorA)
  val scaleFormulaQuestion: Question       = ScaleFormula(majorEFlat)
  val scaleHarmonizationQuestion: Question = ScaleHarmonization(majorC)
  val notesIntervalQuestion: Question      = IntervalBetweenNotes(E, G)

  val scaleNotesAnswer: Answer         = scaleNotesQuestion.answer
  val scaleFormulaAnswer: Answer       = scaleFormulaQuestion.answer
  val scaleHarmonizationAnswer: Answer = scaleHarmonizationQuestion.answer
  val notesIntervalAnswer: Answer      = notesIntervalQuestion.answer

  val goodScaleNoteVerification: AnswerVerification     = AnswerVerification(scaleNotesQuestion, scaleNotesAnswer)
  val goodNotesIntervalVerification: AnswerVerification = AnswerVerification(notesIntervalQuestion, notesIntervalAnswer)
  val wrongNotesIntervalVerification: AnswerVerification =
    AnswerVerification(notesIntervalQuestion, IntervalBetweenNotesAnswer(Some(majorSecond)))
  val wrongScaleFormulaVerification: AnswerVerification =
    AnswerVerification(scaleHarmonizationQuestion, ScaleHarmonizationAnswer(Nil))

  val cMajorScaleJson: String =
    """
      |{
      |  "tonic" : "C",
      |  "type" : "Major"
      |}
      |""".stripMargin

  val questionsJson: String =
    """
      |[
      | {
      |   "type":"ScaleFormula",
      |   "scale":{
      |     "tonic":"Cb",
      |     "type":"Minor"
      |   }
      | },
      | {
      |  "type" : "ScaleHarmonization",
      |  "scale" : {
      |    "tonic" : "C",
      |    "type" : "Major"
      |  }
      | },
      | {
      |  "type" : "ScaleNotes",
      |  "scale" : {
      |    "tonic" : "A",
      |    "type" : "Minor"
      |  }
      | },
      | {
      |   "type":"IntervalBetweenNotes",
      |   "notes":["B#","Bb"]
      | }
      |]
      |""".stripMargin

  val answersJson: String =
    """
      |[
      | {
      |  "type" : "IntervalBetweenNotes",
      |  "interval" : "b3"
      | },
      | {
      |  "type" : "ScaleFormula",
      |  "intervals" : [
      |    "1",
      |    "2",
      |    "3",
      |    "4",
      |    "5",
      |    "6",
      |    "7"
      |  ]
      | },
      | {
      |  "type" : "ScaleHarmonization",
      |  "chords" : [
      |    "C Major",
      |    "D Minor",
      |    "E Minor",
      |    "F Major",
      |    "G Major",
      |    "A Minor",
      |    "B Diminished"
      |  ]
      | },
      | {
      |  "type" : "ScaleNotes",
      |  "notes" : [
      |    "A",
      |    "B",
      |    "C",
      |    "D",
      |    "E",
      |    "F",
      |    "G"
      |  ]
      | }
      |]
      |""".stripMargin

  val goodScaleNoteVerificationJson: String =
    """
      |{
      | "question": {
      |  "type" : "ScaleNotes",
      |  "scale" : {
      |   "tonic" : "A",
      |   "type" : "Minor"
      |  }
      | },
      | "answer": {
      |  "type" : "ScaleNotes",
      |  "notes" : [
      |    "A",
      |    "B",
      |    "C",
      |    "D",
      |    "E",
      |    "F",
      |    "G"
      |  ]
      | }
      |}
      |""".stripMargin
}
