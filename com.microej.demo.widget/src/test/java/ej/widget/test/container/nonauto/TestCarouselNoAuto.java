/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.nonauto;

import ej.annotation.Nullable;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.SelectorHelper;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.PagingIndicator;
import ej.widget.basic.drawing.BulletPagingIndicator;
import ej.widget.container.AbstractCarousel;
import ej.widget.container.FillCarousel;
import ej.widget.container.ListCarousel;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.listener.OnClickListener;

/**
 *
 */
public class TestCarouselNoAuto {

	@Nullable
	private static AbstractCarousel carousel;

	private static int selectedIndex;

	public static void main(String[] args) {
		MicroUI.start();

		Desktop desktop = new Desktop();
		desktop.setStylesheet(createStylesheet());
		AbstractCarousel carousel = new FillCarousel(LayoutOrientation.VERTICAL, true, new BulletPagingIndicator(),
				true);
		carousel = new ListCarousel(LayoutOrientation.VERTICAL, false, new BulletPagingIndicator(), true);
		TestCarouselNoAuto.carousel = carousel;
		fillCarousel(carousel);
		carousel.goTo(3);
		desktop.setWidget(carousel);
		desktop.requestShow();
	}

	private static int count;

	private static void fillCarousel(AbstractCarousel carousel) {
		carousel.removeAllChildren();
		// if ((count & 0x2) == 0) {
		carousel.addChild(new SelectableLabel("Lorem ipsum dolor sit amet " + count++));
		carousel.addChild(new SelectableLabel("Sed non risus. Suspendisse lectus tortor."));
		carousel.addChild(new SelectableLabel("Cras elementum ultrices diam."));
		carousel.addChild(new SelectableLabel("Maecenas ligula massa, varius a."));
		carousel.addChild(new SelectableLabel("Proin porttitor."));
		carousel.addChild(new SelectableLabel("Duis arcu massa."));
		carousel.addChild(new SelectableLabel("Pellentesque congue."));
		carousel.addChild(new SelectableLabel("Ut in risus volutpat libero pharetra tempor."));
		// }
		if ((count & 0x1) != 0) {
			carousel.addChild(new SelectableLabel("Lorem ipsum dolor sit amet"));
			carousel.addChild(new SelectableLabel("Sed non risus. Suspendisse lectus tortor."));
			carousel.addChild(new SelectableLabel("Cras elementum ultrices diam."));
			carousel.addChild(new SelectableLabel("Maecenas ligula massa, varius a."));
			carousel.addChild(new SelectableLabel("Proin porttitor."));
			carousel.addChild(new SelectableLabel("Duis arcu massa."));
			carousel.addChild(new SelectableLabel("Pellentesque congue."));
			carousel.addChild(new SelectableLabel("Ut in risus volutpat libero pharetra tempor."));
		}
	}

	private static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setBackground(NoBackground.NO_BACKGROUND);

		style = stylesheet.getSelectorStyle(new TypeSelector(ListCarousel.class));
		style.setBackground(new RectangularBackground(Colors.WHITE));
		style = stylesheet.getSelectorStyle(new TypeSelector(FillCarousel.class));
		style.setBackground(new RectangularBackground(Colors.WHITE));

		style = stylesheet.getSelectorStyle(new TypeSelector(SelectableLabel.class));
		style.setBackground(new RectangularBackground(Colors.WHITE));
		style.setPadding(new UniformOutline(10));

		style = stylesheet.getSelectorStyle(
				new AndCombinator(new TypeSelector(SelectableLabel.class), new CarouselFocusSelector()));
		style.setBackground(new RectangularBackground(Colors.MAGENTA));

		style = stylesheet.getSelectorStyle(new TypeSelector(PagingIndicator.class));
		style.setPadding(new UniformOutline(2));

		return stylesheet;
	}

	static class CarouselFocusSelector implements Selector {

		@Override
		public boolean appliesToWidget(Widget widget) {
			AbstractCarousel carousel = TestCarouselNoAuto.carousel;
			int selectedIndex = TestCarouselNoAuto.selectedIndex;
			if (carousel == null || selectedIndex < 0 || selectedIndex >= carousel.getChildrenCount()) {
				return false;
			}

			Widget selectedWidget = carousel.getChild(selectedIndex);
			return (selectedWidget == widget);
		}

		@Override
		public int getSpecificity() {
			return SelectorHelper.getSpecificity(0, 0, 1, 0);
		}
	}

	static class SelectableLabel extends Button implements OnClickListener {

		/**
		 * Creates a label with an empty text.
		 */
		public SelectableLabel() {
			this(""); //$NON-NLS-1$
		}

		/**
		 * Creates a label with the given text to display.
		 *
		 * @param text
		 *            the text to display, it cannot be <code>null</code>.
		 * @throws NullPointerException
		 *             if the text is <code>null</code>.
		 */
		public SelectableLabel(String text) {
			super(text);
			setEnabled(true);
		}

		@Override
		public void onClick() {
			AbstractCarousel carousel = TestCarouselNoAuto.carousel;
			assert carousel != null;
			TestCarouselNoAuto.selectedIndex = carousel.getIndexOf(this);
			fillCarousel(carousel);
			carousel.requestLayOut();
		}

		// @Override
		// public void computeOptimalSize(Style style, Size size) {
		// size.setSize(Display.getDisplay().getWidth(), Display.getDisplay().getHeight());
		// }
	}

}
