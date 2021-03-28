package bar.simon.learn.music.domain

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

  val CMaj: Chord = Chord.major(C)
  val DMin: Chord = Chord.minor(D)
  val EMin: Chord = Chord.minor(E)
  val FMaj: Chord = Chord.major(F)
  val GMaj: Chord = Chord.major(G)
  val AMin: Chord = Chord.minor(A)
  val BDim: Chord = Chord.dim(B)

  val intervals = List(
    (A, root, A),
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
    (EFlat, root, EFlat),
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
    (B, root, B),
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
  val majorC: Scale = Scale.major(C)
  // A# B# C## D# E# F## G##
  val majorASharp: Scale = Scale.major(ASharp)
  // Eb F G Ab Bb C D
  val majorEFlat: Scale = Scale.major(EFlat)
  // Fb Gb Ab Bbb Cb Db Eb
  val majorFFlat: Scale = Scale.major(FFlat)

  // A B C D E F G
  val minorA: Scale = Scale.minor(A)
  // E F# G A B C D
  val minorE: Scale = Scale.minor(E)
  // D# E# F# G# A# B C#
  val minorDSharp: Scale = Scale.minor(DSharp)
  // Bb C Db Eb F Gb Ab
  val minorBFlat: Scale = Scale.minor(BFlat)

  val scaleNotesQuestion: Question         = ScaleNotes(minorA)
  val scaleFormulaQuestion: Question       = ScaleFormula(majorEFlat)
  val scaleHarmonizationQuestion: Question = ScaleFormula(majorC)
  val notesIntervalQuestion: Question      = IntervalBetweenNotes(E, G)
}
