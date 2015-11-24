# Overview
This demo illustrates the widgets library based on MicroUI-2.0 and MWT2.0. The main page allow to lead to all the pages of the application illustrating the following widgets:
- button (with text or pictogram),
- label,
- scrolling list,
- scroll bar 
- slider,
- radio button, 
- check-box, 
- switch, 
- progress bar, 
- vertical lists.

This demo includes MicroEJ launchers configuration for the following Java platforms:
- simulator
- ST STM32F746G-DISCO

# Project Setup

First of all, you have to download the entire repository by using the `Download` button or by cloning the repository. After having retrieved the repository content, open MicroEJ and then import _Existing project into workspace_ by selecting either the ZIP file or the root directory.

## Requirements

- JRE 7 (or later) x86.
- MicroEJ 3.1 or later.
- Java platform with (at least): MICROUI-2.0, MWT-2.1.0, EDC-1.2.
- Hardware: this demo has been tested on ST STM32F746G-DISCO (480x272 display) board.

## Project structure

  - `src/main/java`
  	- Java sources
  		com.microej.demo.widgets.page: pages of the demo.
  		com.microej.demo.widgets.style: look and feel management of the demo.
  		Widgets: entry point of the demo.
  - `src/main/resources`: images, fonts...
  - `launches/`: MicroEJ launches in the project `com.microej.demo.widgets`

# Usage
To launch the application, right-click on the project, select _Run as_, _MicroEJ Application_ and choose _Widgets (Simulation)_. Another launcher is available to execute on the target board.

# Changes
- November 2015: initial version

# License
See the license file `LICENSE.md` located at the root of this repository.
