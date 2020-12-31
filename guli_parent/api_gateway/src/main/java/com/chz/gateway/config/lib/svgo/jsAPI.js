due to filtering, #10834
- Fixed clearable Input still showing the clear icon when its initial value is `null`, #10912
- Fixed incorrect trigger of the `active-change` event after changing ColorPicker's binding value programatically, #10903 (by @zhangbobell)
- Fixed filterable Select causing an infinite loop when navigating options using keyboard if all options are disabled, #10945

### 2.3.6

*2018-04-21*

- Fixed wrong behavior of Tree's `allow-drop` callback when `type` parameter is used, #10821
- Now you can properly enter keywords in filterable single Select in IE11, #10822
- Fixed single Select incorrectly triggering `blur` event after clicking an option, #10822

### 2.3.5

*2018-04-20*

- Fixed incorrect highlights in DatePicker panel when `type` is week, #10712
- Fixed InputNumber being empty when its initial value is 0, #10714
- Added `automatic-dropdown` attribute for Select, #10042 (by @Seebiscuit)
- Fixed disabled Rate's value still being updated by navigation keys, #10726 (by @Richard-Choooou)
- Now DatePicker's `type` attribute can be `'dates'`, where you can pick multiple dates in one picker, #10650 (by @Mini256)
- Added `prev-click` and `next-click` events for Pagination, #10755
- Added `pager-count` attribute for Pagination, #10493 (by @chongjohn716)
- Added `type` as the 3rd param of Tree's `allow-drop` attribute callback, #10792
- Now we use ResizeObserver to detect DOM element resizing, #10779

### 2.3.4

*2018-04-12*

- Deleted duplicate `showTimeout` attribute in SubMenu's TypeScript declaration, #10566 (by @kimond)
- Now you can customize Transfer's data item using scoped slot, #10577
- Fixed clicking disabled prev and next button of Pagination still triggers `current-change` event, #10628
- Fixed Textarea displaying `undefined` in SSR when its value is not set, #10630
- Fixed disabled TabItem style when `type` is border-card, #10640
- Added `$index` as `formatter`'s fourth param of Table, #10645
- Fixed CheckboxButton not exported in TypeScript declaration, #10666

### 2.3.3

*2018-04-04*

- Added `shadow` attribute for Card, #10418 (by @YunYouJun)
- Fixed Badge being hidden when `value` is `0`, #10470
- Fixed some bugs of draggable Tree, #10474 #10494
- Added `placement` for Autocomplete, #10475
- Now `default-time` attribute also works in non-range DateTimePicker, #10321 (by @RickMacTurk)
- Removed the blue outline of TabItem after the browser blurs or is minimized, #10503
- Added `popper-append-to-body` attribute for SubMenu, #10515
- Removed visual feedback when hovering on non-link BreadcrumbItem, #10551
- Fixed InputNumber's `change` event to ensure the component's binding value is updated in the event handler, #10553

### 2.3.2

*2018-03-29*

- Fixed an Autocomplete regression, #10442

### 2.3.1

*2018-03-29*

- Fixed a regression that `type` of Input is not passed down to the native input element, #10415
- Added `blur` method for Select, #10416

### 2.3.0 Diamond

*2018-03-28*

#### New features
- Table
  - Now `formatter` of TableColumn can be dynamically updated, #10184 (by @elfman)
  - Added `select-on-indeterminate` attribute, #9924 (by @syn-zeta)
- Menu
  - Added `collapse-transition` attribute, #8809 (by @limichange)
- Input
  - Added `select` method, #10229
  - Added `blur` method, #10356
- ColorPicker
  - Added `predefine` attribute, #10170 (by @elfman)
- Tree
  - Added `draggable`, `allow-drop` and `allow-drag` attributes, and `node-drag-start`, `node-drag-enter`, `node-drag-leave`, `node-drag-over`, `node-drag-end` and `node-drop` events, #9251 #10372 (by @elfman)
- Form
  - `validate` method now has a second parameter, containing information of form items that failed the validation, #10279
  - Added `validate` event, #10351
- Progress
  - Added `color` attribute, #10352 (by @YunYouJun)
- Button
  - Added `circle` attribute, #10359 (by @YunYouJun)

#### Bug fixes
- Form
  - Fixed label of FormItem not align with mixed Input, #10189
- Menu
  - Now collapsed Menu will only show the Tooltip when the `title` slot of MenuItem is set, #10193 (by @PanJiaChen)
- Pagination
  - Fixed `current-change` event wrongly triggering without user interaction, #10247
- DatePicker
  - Now the date and time value in the dropdown panel are correctly formatted based on the `format` attribute, #10174(by @remizovvv)
- Upload
  - Fixed `accept` attribute not working when `drag` is true, #10278

### 2.2.2

*2018-03-14*

- Added `clear` event for Input, #9988 (by @blackmiaool)
- Now manual input of ColorPicker supports `hsl`, `hsv` and `rgb` modes, #9991
- Fixed DatePicker not triggering `change` event when its initial value is cleared, #9986
- Now icon class related attributes of Rate support dynamic updates, #10003
- Fixed Table with fixed columns not updating its height correctly if `max-height` is set, #10034
- Now DatePicker's range mode supports reverse selection (clicking the end date, then clicking the start date), #8156 (by @earlymeme)
- Added `disabled` attribute for Pagination, #10006
- Added `after-enter` and ` after-leave` events for Popover, #10047
- Fixed Select not triggering validation when user selects an option after executing `resetFields` of Form, #10105
- Fixed incorrect widths of fixed columns of Table in some cases, #10130
- Fixed MessageBox inheriting the `title` attribute of its previous instance when called without `title`, #10126 (by @Pochodaydayup)
- Added `input-size` attribute for Slider, #10154
- Added `left-check-change` and `right-check-change` events for Transfer, #10156

### 2.2.1

*2018-03-02*

- Fixed Aside, Header and Footer shrinking in some layout, #9812
- Fixed Table with a `height` attribute not rendering in SSR, #9876
- Fixed expandable Table not calculating its height when a row is expanded, #9848
- Fixed `change` event not trigger when manually typing date in DateTimePicker, #9913
- Fixed Select showing its options when the input box is right-clicked, #9894 (by @openks)
- Added `tooltip-class` attribute for Slider, #9957
- Now Select will stay focused after selection, #9857 (by @Seebiscuit)
- Added `target-order` attribute for Transfer, #9960

### 2.2.0 Graphite

*2018-02-12*

#### New features
- Menu
  - Added `popper-class` and `disabled` attributes for SubMenu, #9604 #9771
  - Horizontal Menu now supports multi-layered SubMenu, #9741
- Tree
  - Added `node-contextmenu` event, #9678
  - Now you can customize node template using scoped slot, #9686
  - Added `getNode`, `remove`, `append`, `insertBefore`, `insertAfter`, `getCheckedKeys`, `getHalfCheckedNodes`, `getHalfCheckedKeys` methods and `check` event, #9718 #9730
- Transfer
  - Added `clearQuery` method, #9753
- Select
  - Added `popper-append-to-body` attribute, #9782

#### Bug fixes
- Table
  - Fixed clicking expanding icon of an expandable row triggers `row-click` event, #9654
  - Fixed layout not update when column width is changed by user dragging, #9668
  - Fixed style issue when summary row co-exists with fixed columns, #9667
- Container
  - Fixed container components not stretching in IE11, #9655
- Loading
  - Fixed Loading not showing when the value of `v-loading` is changed to true in the `mounted` hook, #9722
- Switch
  - Fixed two native click events are triggered when Switch is clicked, #9760

### 2.1.0 Charcoal

*2018-01-31*

#### New features
- Cascader
  - Added `focus` and `blur` events, #9184 (by @viewweiwu)
- Table
  - The `filter-method` now has a third param `column`, #9196 (by @liyanlong)
- DatePicker
  - Added `prefix-icon` and `clear-icon` attributes, #9237 (by @AdamSGit)
  - Added `default-time` attribute, #9094 (by @nighca)
  - `value-format` now supports `timestamp`, #9319 (by @wacky6)
- InputNumber
  - Now the binding value can be `undefined`, #9361
- Select
  - Added `auto-complete` attribute, #9388
- Form
  - Added `disabled` attribute, #9529
  - Added `validateOnRuleChange` attribute, #8141
- Notificaition
  - Added `closeAll` method, #9514

#### Bug fixes
- InputNumber
  - Fixed value resetting when typing decimal point, #9116
- Dropdown
  - Fixed dropdown menu incorrect positioning when the page only has a horizontal scrollbar in some browsers, #9138 (by @banzhuanmei)
- Table
  - Fixed an error in calculating number of fixed columns after the column data changes, #9188(by @kolesoffac)
  - Fixed the border of the last column of the grouped header not properly displayed, #9326
  - Fixed incorrect positioning of table header in Safari, #9327
  - Fixed expanded row collapsing when the table data changes, #9462
  - Fixed unnecessary multiple renders in some conditions, #9426
  - Fixed column width calculation error when `width` of TableColumn changes, #9426
- Loading
  - Fixed Loading not hiding correctly in some conditions, #9313
- DatePicker
  - Fixed `focus` method not working in range mode, #9437
  - Fixed clicking the "now" button still selecting the current date even if it is disabled, #9470 (by @wacky6)
  - Fixed date clamping when navigating, #9577 (by @wacky6)
- Steps
  - Fixed style error in IE 11, #9454

#### Breaking changes
- Menu
  - The popup menu in `collapse` mode now appends directly to `body`, so that it is visible when neste