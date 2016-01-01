# TODO/Task list # {#todo}

[TOC]

# Planned Releases # {#todo_planned}
Place tasks for each release as planned, once completed, remove tasks and place it in the release notes.

## 0.1 (29-06-2015) ## {#todo_0.1}
### Fixes ###


### New Features ###

### Documentation ###

### Unit Testing/Logging ###

# Others # {#todo_others}


- support chart

- Introduce cached cells for current page (formula) and chart. So when refresh current page, will cache all those values. Only changed value will be rendered (refresh)
  to the client side via ajax call

- support primefaces inPlace component for editing. need (1) inPlaceMode attribute (2) mouse over the editable cell, click then have ok cancel pop up.
  The idea is for small change forms which how up nicely with format
  
- support offline editing. User can download the template and edit is as normal excel, then upload again. Web form only retrieve the editable cells as for user's input.
  this will allow users to use Excel to input large amount data. 

- allow only specify row number for header and body range. therefor when template expand columns, do not need to change configuration
- create style array for web for saving memory
- User px or percentage for column width depend on user's preference as current percentage doesn't work well in some cases.
- form width not handled well
- formula with range not handled well. looks like it's POI bug
- value change event refresh cell list should be cached instead of iterate through entire page.
- support excel data validation
- support excel comments
- Add unit test for web sheet


#Done list
- use readonly attribute for viewer
- Setup 100% style as default for old version template
- Input data with actual value instead of formatted string
- For null cell do not create blank cell instead use null as poicell in facescell and show blank on web
- Determind user input data type before set as cell value
- Fixed bug for load heading. Now support Row/Col span nicely
- Change the way for loading body. For repeat type, populate the body row first, then assamble the datatable.
- Now body also support Row/Col span 
- Add font/color support for body
- Add version to Configuration Tab. This will enable future change on configuration structures.
- Add form width, max row per pages to configuration tab
- Now hidden column/rows handled
- Support no configuration tab sheet. E.g. load pure Excel Spreadsheet. If found no configuration, then assume user want to load the entire workbooks as web sheets.
- Add image(picture) support
- Add row height support
- Fix issue with color not correct if user use customized color palate
- implement insert/delete row for repeat type form
- Support add/delete row in Repeat style form
- Support percentage input. e.g. user can input 20% same with 0.20 for percentage cells





