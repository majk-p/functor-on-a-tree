# Marp presentation theme

## Usage example

Just make a copy of this repository and fill in `slides.md`. For editor support install [marp-vscode](https://github.com/marp-team/marp-vscode).

When developing your presentation, the recommended flow is:
* Fork this repository
* Run `./live-reload-html.sh`
* The script will print following text: `Running live reload, the content will be rendered to $PATH` where `$PATH` is the path to html file
* Open the `$PATH` in the browser of your choice. Keep in mind that slide transitions might only work in chrome
* Edit the `slides.md`, the rendered presentation will reload live in your browser
* When you're done you might want to use `./render-pdf.sh` to produce a PDF file for offline backup and easy sharing

### Syntax

Here's the most basic usage example for creating a presentation with Marp would look like this:

````
---
theme: mp-theme
size: 16:9
transition: slide 
marp: true
---

<!-- _class: intro -->
# Presentation intro slide

---

# Regular slide

Presenting some interesting content

---
<!-- _class: divider -->
# New section divider

Default divider has a blue background

---
# Slide with code

```scala
@main
def main() =
  println("hello world")
```
````

The `---` is the separator of slides, each slide starts with it, make sure it's surrounded by empty lines.

The output for the example code would look like this when converted to pdf using `render-pdf.sh`:

![](./readme-images/marp-example-output.png)


Check out the `slides.md` for a more exhaustive presentation example. You can render by cloning this repo and running `render-pdf.sh`!

### Reference 

For more Marp syntax check out:

* https://marpit.marp.app/markdown
* https://marpit.marp.app/image-syntax
* https://github.com/marp-team/marp-core
* https://github.com/marp-team/marp-cli
* Or search https://github.com/orgs/marp-team/discussions/

## Rendering

### Prerequisites

Please make sure you have the [`npx`](https://docs.npmjs.com/cli/v9/commands/npx?v=true) command available. To install it just make sure to install latest `npm` LTS version.

### Scripts

This repository comes with three scripts that make it easier to render the presentation:

* `render-pdf.sh` renders your presentation as a PDF document
* `render-html.sh` renders it as HTML - this is most useful when you want slide transitions
* `live-reload-html.sh` similarly to previous one, renders html but keeps on listening to the file changes and re-renders upon change

All results are by default rendered to `./output` directory

## Additional resources

Free images with permissive licenses:
* https://unsplash.com
* https://freeimages.com
* https://freepik.com
* https://pixabay.com
* https://pexels.com/
* https://undraw.co/illustrations

Graphs and diagrams:
* https://excalidraw.com/
* https://app.diagrams.net/
* https://www.canva.com/graphs/