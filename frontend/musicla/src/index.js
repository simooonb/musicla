import React from 'react';
import ReactDOM from 'react-dom/client';
import axios from 'axios'
import './index.css';

class Question extends React.Component {
  constructor(props) {
    super(props);
  }

  getLabel(question) {
    switch (question.type) {
      case "ScaleFormula":
        return "What is the formula of a " + question.scale.type + " scale?"
        break;
  
      case "ScaleNotes":
        return "What are the notes of " + question.scale.tonic + " " + question.scale.type + "?"
        break;
  
      case "ScaleHarmonization":
        return "What is the chord harmonization of " + question.scale.tonic + " " + question.scale.type + "?"
        break;
  
      case "IntervalBetweenNotes":
        return "What is the interval between " + question.notes[0] + " and " + question.notes[1] + "?"
        break;
    
      default:
        console.log("Unknown question type!")
        break;
    }
  }

  render() {
    return (
      <div key={this.props.question.id}>
        {this.getLabel(this.props.question)}
        <SubmitAnswerForm question={this.props.question} />
        <FetchAnswerButton question={this.props.question} />
      </div>
    )
  }
}

class SubmitAnswerForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      answerInput: '',
      resultLabel: null
    };
    this.handleChange.bind(this);
    this.handleSubmit.bind(this);
  }

  handleChange(event) {
    console.log(event.target.value);
    this.setState({
      answerInput: event.target.value
    });
  }

  async handleSubmit(event) {
    event.preventDefault();
    console.log("submitted " + this.state.answerInput);

    const answerVerificationBody = {
      "question": this.props.question,
      "answer": this.state.answerInput
    }

    await axios.post("http://localhost:8080/api/answers/verify", answerVerificationBody)
      .then((response) => {
        const result = response.data;
        console.log(result)
        if (result) {
          this.setState({
            resultLabel: "Good answer!"
          });
        } else {
          this.setState({
            resultLabel: "Wrong answer!"
          });
        }
      });
  }

  // TODO: different kind of form based on the question + parsing server-side
  render() {
    const resultNotReceived = this.state.result == null;

    if (resultNotReceived) {
      return (
        <form onSubmit={(e) => this.handleSubmit(e)}>
          <label>
            Answer: 
            <input type="text" value={this.state.answerInput} onChange={(e) => this.handleChange(e)} />
          </label>
          <input type="submit" value="Submit answer" />
        </form>
      )
    } else {
      <div id="answer-result">{this.state.resultLabel}</div>
    }
  }
}

class FetchAnswerButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      answerLabel: "get answer",
      answerType: null
    }
  }

  async handleClick() {
    await axios.post("http://localhost:8080/api/answers", this.props.question)
      .then((response) => {
        const answer = response.data;
        handleQuestionType(
          answer,
          (answer) => {
            this.setState({
              answerType: answer.type,
              answerLabel: answer.type + " " + answer.intervals.join(', ')
            })
          },
          (answer) => {
            this.setState({
              answerType: answer.type,
              answerLabel: answer.type + " " + answer.notes.join(', ')
            })
          },
          (answer) => {
            this.setState({
              answerType: answer.type,
              answerLabel: answer.type + " " + answer.chords.join(', ')
            })
          },
          (answer) => {
            this.setState({
              answerType: answer.type,
              answerLabel: answer.type + " " + answer.interval
            })
          },
        )
      });
  }

  render() {
    const answerNotReceived = this.state.answerType == null;

    if (answerNotReceived) {
      return (
        <button onClick={() => this.handleClick()}>{this.state.answerLabel}</button>
      )
    } else {
      return (
        <div id="answer">{this.state.answerLabel}</div>
      )
    }
  }
}

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      question: null,
      isLoaded: false,
      error: null
    };
  }

  componentDidMount() {
    this.fetchNewQuestion();
  }

  fetchNewQuestion() {
    axios.get("http://localhost:8080/api/questions/random")
      .then(
        (response) => {
          console.log(response.data);
          const question = response.data[0];
          this.setState({
            isLoaded: true,
            question: question
          })
        }
      )
      .catch(
        (error) => {
          console.log(error);
          this.setState({
            isLoaded: true,
            error: error
          })
        }
      )
  }

  render() {
    const error = this.state.error;
    const isLoaded = this.state.isLoaded;

    if (error) {
      return <div>error: {error.message}</div>
    } else if (!isLoaded) {
      return <div>Loading...</div>
    } else {
      return (
        <div id="app">
          <Question question={this.state.question} />
          <button onClick={() => this.fetchNewQuestion()}>New question</button>
        </div>
      )
    }
  }
}

function handleQuestionType(question, scaleFormulaFn, scaleNotesFn, scaleHarmFn, intervalFn) {
  switch (question.type) {
    case "ScaleFormula":
      scaleFormulaFn(question)
      break;

    case "ScaleNotes":
      scaleNotesFn(question)
      break;

    case "ScaleHarmonization":
      scaleHarmFn(question)
      break;

    case "IntervalBetweenNotes":
      intervalFn(question)
      break;
  
    default:
      console.log("unknown question type!")
      break;
  }
}

// ========================================

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);
