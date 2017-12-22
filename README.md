# Overview
This demo illustrates the Widget library (`ej.widget`, `ej.style`, `ej.animation`, `ej.color`) based on MicroUI 2 and MWT 2.
The main page allows to lead to all the pages of the application illustrating the following widgets:

* button,
* label,
* scrolling list,
* scroll bar,
* slider,
* radio button,
* check box,
* switch,
* progress bar,
* vertical lists,
* keyboard,
* chart,
* wheel,
* carousel.

# Rendering
This demo includes various implementations of widgets and screen transitions.

## Vector drawings
Widgets are drawn using 2D primitives with and without anti-aliasing. This approach uses more CPU time but no ROM (no graphic resources except the code itself).

See `GraphicsContext` and `AntiAliasedShapes` classes for more information. 

## Raster graphics (images)
Widgets are drawn using images. This approach requires more ROM depending on the size and number of images used. It is most of the time faster than vector drawing and requires less CPU load.

Images can be either embedded encoded (PNG for example, decoded at runtime) or embedded as bitmaps (directly drawn to the display, the conversion is done on at build time).

See UI reference manual for more information.

## Pictograms (monochrome images)
Widgets are drawn using monochrome images that can be colored dynamically. This approach requires less ROM than using colorful images but can only be applied for monochrome elements.

It can be achieved using fonts (MicroEJ font designer can import images into a font) or monochrome images with alpha transparency.

See UI reference manual for more information.

# Transitions
This demo includes two implementations of screens transition.

## Snapshot
Screens are dynamically created as images (buffer in RAM) for the transition. This approach requires more RAM (twice the size of the screen in buffers) but is faster than the 2nd implementation.

## Dynamic
Screens are drawn while moving. For each step of the transition, all widgets are drawn in the moving screen. This approach uses more CPU but less RAM. Transition speed might be slower depending on the number of widgets to draw.


# Project Setup
First of all, you have to download the entire repository by using the `Download` button or by cloning the repository. After having retrieved the repository content, open MicroEJ and then import _Existing project into workspace_ by selecting either the ZIP file or the root directory.

## Requirements
* MicroEJ Studio or SDK 4.1 or later
* A platform with at least:
  * EDC-1.2 or higher
  * MICROUI-2.0 or higher
  * MWT-2.1 or higher
* Hardware: this demo has been tested on ST STM32F746G-DISCO (480x272 display) board.

## Project structure
* `src/main/java`: Java sources.
  * `com.microej.demo.widget.keyboard`: layouts of the keyboard.
  * `com.microej.demo.widget.page`: pages of the demo.
  * `com.microej.demo.widget.style`: look and feel management of the demo.
  * `ej.widget`: additional widgets.
  * `MainActivity`: activity of the sandboxed demo.
  * `WidgetsDemo`: entry point of the standalone demo.
* `src/main/resources`: images, fonts…

# Usage
## Run on MicroEJ Simulator
1. Right Click on the project
1. Select **Run as -> MicroEJ Application**
1. Select your platform 
1. Press **Ok**

## Run on device
### Build
1. Right Click on [WidgetsDemo.java](ej.demo.ui.widget/src/main/java/com/microej/demo/widget/WidgetsDemo.java)
1. Select **Run as -> Run Configuration**
1. Select **MicroEJ Application** configuration kind
1. Click on **New launch configuration** icon
1. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform (but not a virtual device)
	1. In **Execution** frame
		1. Select **Execute on Device**
		1. In **Settings** field, select **Build & Deploy**
1. Press **Apply**
1. Press **Run**
1. Copy the generated `.out` file path shown by the console

### Flash
1. Use the appropriate flashing tool.

# MicroUI vs. MWT+Widget
By default, consider using MWT+Widget.
It is however possible to develop a GUI with MicroUI without MWT+Widget libraries:

* when there is no or little user interaction,
* for simple screens and a limited number of widgets,
* for simple screen layout,
* for small screens (which implies they need to be simple!),
* for quick image-based design tests.

# Changes
See the change log file [CHANGELOG.md](CHANGELOG.md) located at the root of this repository.

# License
See the license file [LICENSE.md](LICENSE.md) located at the root of this repository.
