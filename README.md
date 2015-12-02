# Overview
This demo illustrates the Widget library based on MicroUI 2 and MWT 2.
The main page allows to lead to all the pages of the application illustrating the following widgets:
- button,
- label,
- scrolling list,
- scroll bar,
- slider,
- radio button,
- check box,
- switch,
- progress bar,
- vertical lists.

# Rendering
This demo includes various implementations of basic widgets.

## Vectored drawings
Widgets are drawn using 2D primitives with and without anti-aliasing. This approach uses more CPU time but no ROM.

See `GraphicsContext` and `AntiAliasedShapes` classes for more information. 

## Raster graphics (images)
Widgets are drawn using images. This approach requires more ROM depending on the size and number of images used. It is most of the time faster and uses few CPU.

Images can be embedded in either encoded (PNG for example) or different type of bitmaps. In the first case, the images need to be decoded at runtime. In the second case, the image can be directly drawn to the display (the conversion is done on the PC). 
See UI reference manual for more information.

## Pictograms (monochrome images)
Widgets are drawn using monochrome images that can be colored dynamically. This approach requires less ROM than using colorful images but can be applied only for monochrome elements.

It can be achieve either using the fonts, either using alpha images.
See UI reference manual for more information.

# Transitions
This demo includes two implementations of screens transition.

## Snapshot
Screens are dynamically created as images (buffer in RAM) for the transition. This approach requires more RAM (twice the size of the screen in buffers) but is a lot faster.

## Dynamic
Screens are drawn while moving. For each step of the transition, all widgets are drawn in the moving screen. This approach uses more CPU but a few RAM. It may be slow depending on the content of the screens.

# Project Setup
First of all, you have to download the entire repository by using the `Download` button or by cloning the repository. After having retrieved the repository content, open MicroEJ and then import _Existing project into workspace_ by selecting either the ZIP file or the root directory.

## Requirements
- JRE 7 (or later) x86.
- MicroEJ 4.0 or later.
- Java platform with (at least): MICROUI-2.0, MWT-2.x, EDC-1.2.
- Hardware: this demo has been tested on ST STM32F746G-DISCO (480x272 display) board.

## Project structure
  - `src/main/java`: Java sources.
  		`com.microej.demo.widgets.page`: pages of the demo.
  		`com.microej.demo.widgets.style`: look and feel management of the demo.
  		`WidgetsDemo`: entry point of the demo.
  - `src/main/resources`: images, fonts…
  - `launches/`: MicroEJ launches.

# Usage
This demo includes MicroEJ launchers configuration for the following Java platforms:
- ST STM32F746G-DISCO (simulator & board).

To launch the application, right-click on the project, select _Run as_, _MicroEJ Application_ and choose _Widgets (Simulation)_. Another launcher is available to execute on the target board.

# MicroUI vs. MWT
By default, consider using MWT.
MicroUI may be chosen:
* when there is no or little user interaction,
* for simple screens,
* for small screens (that implied they need to be simple!),
* for quick image-based design tests.

# Changes
- December 2015: initial version.


# License
See the license file `LICENSE.md` located at the root of this repository.