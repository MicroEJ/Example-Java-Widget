# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [7.5.1] - 2022-10-20

### Fixed

- Fix Widget library version in README.md.

## [7.5.0] - 2022-10-18

### Added

- Add CircularSlider widget and page.
- Add DoubleTemperatureSlider widget and page.
- Add FixedGrid page.

### Fixed

- Use Util.platformTimeMillis() instead of System.currentTimeMillis() for time elapsing computing.

## [7.4.0] - 2022-01-21

### Added

- Add a start method to launch the application.
- Add a method to allow page insertion before application starts.
- Add SnapScroll container and page.
- Add SecretScroll container and page.
- Add BufferedScroll widget and page.

### Changed

- Update MWT dependency to 3.3.0.
- Update enabled widgets to use new Widget constructor.

### Fixed

- Clean-up keyboard code.
- Fix colors / pictos used on keyboard.
- Add anti-aliasing to circle progress widgets.
- Fix cropped circle progress widgets.
- Fix carousel spacing to avoid overlapping.
- Add anti-aliasing to line chart pages.

## [7.3.0] - 2021-08-03

### Added

- Add SelectableList page and AnimatedChoice widget.

### Fixed

- Fix launchers to use latest STM32F7508 platform on GitHub.

## 7.2.0 - 2021-05-10

### Added

- Add ImageWidget page.
- Add Carousel widget and page.
- Add SliderWithValue widget and page.
- Add SliderWithProgress widget and page.
- Add Wheel widget and page.
- Add CircularIndeterminateProgress widget and page.
- Add CircularDottedProgress widget and page.
- Add CircularProgress widget and page.
- Add LineWrappingLabel widget in the ScrollableText page.
- Add LineChart widget and page.
- Add BarChart widget and page.
- Add AutoscrollLabel widget and page.
- Add Keyboard widget and page.

### Fixed

- Update MWT, Widget and Motion dependencies.
- Use desktop animator.
- Fix title bar on big displays.

## [7.0.5] - 2020-12-10

### Fixed

- Clean-up dependencies.

## [7.0.4] - 2020-12-04

### Fixed

- Update dependencies to workaround SDK issues (null analysis & MMM).

## [7.0.3] - 2020-11-26

### Changed

- Update README.md.

## [7.0.2] - 2020-11-12

### Added

- Add launcher for simulator.

### Fixed

- Fix animation delay when switching page.

## [7.0.1] - 2020-11-03

### Added

- Add launcher with optimized memory options.

### Changed

- Change page transition to avoid using images heap.
- Decrease number of items in scrollable list.
- Update minimum and maximum length of scrollbars.
- Update blurry characters in font.

## [7.0.0] - 2020-09-21

### Changed

- Revamp all demo on MicroUI 3 and MWT 3.

## [6.2.0] - 2019-10-29

### Changed

- Update to MicroEJ 5.1.
- Use Firmware Single App build type.

### Added

- Add standalone.types.list and standalone.properties.list to specify required types for standalone run

## [6.1.1] - 2018-10-22

### Fixed

- Fix API versions.

## [6.1.0] - 2017-12-27

### Added

- Add carousel page.
- Manage key repeat in keyboard.
- Update on Widget 2.3.x.
- Simplify keyboard page.
- Use A8 image format when possible.
- Clean-up stylesheet.

### Fixed

- Fix keyboard text overflow.
- Fix initial animation of chart.
- Review pages structure.
- Fix buttons' corners.

## [6.0.0] - 2017-01-30

### Added

- Change overall demo design.
- Update LibWidget version.
- Add keyboard page.
- Add chart page.
- Add wheel page
- Sandbox the application.

## [5.0.1] - 2016-07-01

### Fixed

- Fix Widget version.

## [5.0.0] - 2016-06-28

### Added

- Initial revision.

---
_Markdown_ 
_Copyright 2016-2022 MicroEJ Corp. All rights reserved._
_Use of this source code is governed by a BSD-style license that can be found with this software._
