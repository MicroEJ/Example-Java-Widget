# Overview
This demo represents a settings menu you can find on your smartphone. The main page show all available settings, each one leading to a page illustrating a different widget such as:
- button (with text or pictogram),
- label,
- scrolling list/text,
- scroll bar 
- wheel (with font zoom in/out),
- slider,
- radio button, 
- check-box, 
- switch, 
- progress bar, 
- horizontal/vertical lists.

This demo includes MicroEJ launchers configuration for the following Java platform:
- simulator
- ST STM32F429I-EVAL
- ST STM32429I-DISCO

Each launcher can be configured in Run -> Run configuration... -> MicroEJ Application -> Widgets XXXX -> JRE: set VM arguments to the specific options values.
- `-Dmicroej.java.property.com.is2t.demo.NoLayer=SET` to deactivate the layers in the transitions between pages.
- `-Dmicroej.java.property.com.is2t.demo.NoAnimation=SET` to deactivate the transitions between pages.
By default, layers and transitions are enabled.

# Project Setup

First of all, you have to download the entire repository by using the `Download` button or by cloning the repository. After having retrieved the repository content, open MicroEJ and then import _Existing project into workspace_ by selecting either the ZIP file or the root directory.

## Requirements

- JRE 7 (or later) x86.
- MicroEJ 3.1 or later.
- Java platform with (at least): MICROUI-1.5, MWT-1.0, EDC-1.2, MICROUI-LAYER-2.0.0.
- Hardware: this demo has been tested on ST STM32F429I-EVAL (480x272 display) and STM32429I-DISCO (240x320 display) boards.

## Project structure

  - `src/`
  	- Java sources
  		com.is2t.demo.widgets.automaton: automaton of the demo.
  		com.is2t.demo.widgets.page: pages of the demo and flow management between page.
  		com.is2t.demo.widgets.theme: look and feel management of the demo.
  		com.is2t.demo.widgets.widget: specific widgets and renderers of the demo.
  		Widgets: entry point of the demo.
  	- Resources: images, fonts...
  - `launches/`: MicroEJ launches in the project `com.is2t.demo.widgets`
  - `lib/`: to access each library javadoc, open the <libraryname>-javadoc.jar (unzip)
  	- com.is2t.demo.utilities.jar: useful functions like automaton, colorhelper...
  	- com.is2t.demo.layers.jar: GUI layers management
  	- com.is2t.demo.transition.jar: specific transition library with layer
  	- ej.components.jar: component framework library
  	- ej.flow.jar: navigation management library
  	- ej.flow.mwt.jar: page navigation library
  	- ej.motion.jar: motion library 
	- widgets.jar: Widgets library
T

# Usage
To launch the application, right-click on the project, select _Run as_, _MicroEJ Application_ and choose _Widgets (Simulation)_. Another launcher is available to execute on the target board.

# Changes
- April 2015: initial version

# License
See the license file `LICENSE.md` located at the root of this repository.
