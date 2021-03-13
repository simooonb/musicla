package bar.simon.learn.music.domain

import bar.simon.learn.music.domain.Alteration._

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
}
