# JavaFX Application Template

This README provides a comprehensive guide on how to use and extend the JavaFX application template.

---

## 1. Add a New View

### Steps:
1. **Copy an FXML File**:  
   - Navigate to the `FXML/App` folder.  
   - Copy one of the FXML files (except `app.fxml` and `menu.fxml`) and edit it.  
   - **Reminder**: Change the controller associated with the new FXML file.

2. **Update Menu.fxml**:  
   - Open `FXML/App/Menu.fxml`.  
   - Copy an existing button, then rename its `fx:id` and text to suit your needs.

3. **Add a New ENUM**:  
   - Go to `MENU_OPTION` in `View/App` and add a new ENUM.

4. **Modify ApplicationViewFactory**:  
   - Create a new `AnchorPane`.  
   - Add a method to extract the `AnchorPane` from the new FXML file (follow the existing methods as a guide).

5. **Update Menu_Controller**:  
   - Extract the new `fx:id` in `Controller/App/Menu_Controller`.

6. **Set Button Actions**:  
   - Add an `onAction` event to the new button to trigger opening the view when clicked.  
   - Example:
     ```java
     ViewCentral.getInstance().getApplicationViewFactory()
                .setSelectedMenuOption(MENU_OPTION.[YOUR_NEW_ENUM]);
     ```

7. **Set Button Icon (Optional)**:  
   - Customize the button icon if required.

---

## 2. Creating a Controller

### Steps:
1. **Create a New Controller**:  
   - Add a new controller in `Controller/App`.

2. **Link FXML to Controller**:  
   - Connect your new FXML file to the created controller.

3. **Extract FXML IDs**:  
   - Extract all `fx:id` values from the FXML file and define corresponding variables in the controller.

4. **Implement Required Interfaces**:  
   - Implement `Initializable` and, if applicable, `CRUDControllerInterface`.

5. **Define Methods**:  
   - Add methods such as `decor()`, `setupData()`, `insertData()`, and `setButtonActions()`.

6. **Initialize Controller**:  
   - Call the defined methods inside the `initialize()` method.

---

## 3. Notification and Confirm

### Notification Types:
- `NOTIFICATION_TYPE.WARNING`: Yellow color with an alert icon.  
- `NOTIFICATION_TYPE.SUCCESS`: Green color with a check icon.  
- `NOTIFICATION_TYPE.INFOR`: Blue color with an "info" icon.

### Usage:
1. **Push Notification (Auto-Disappear)**:  
   - Disappears automatically after 5 seconds.  
   - Example:
     ```java
     ViewCentral.getInstance().getStartViewFactory()
                .pushNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Example Message");
     ```

2. **Stand-On Notification (Persistent)**:  
   - Remains visible until manually closed.  
   - Example:
     ```java
     ViewCentral.getInstance().getStartViewFactory()
                .standOnNotification(NOTIFICATION_TYPE.WARNING, anchorPane, "Example Message");
     ```

3. **Confirmation Message**:  
   - Returns `TRUE` if "OK" is clicked; otherwise, `FALSE`.  
   - Example:
     ```java
     if (!ViewCentral.getInstance().getStartViewFactory().confirmMessage("Are you sure?")) {
         return;
     }
     ```

---

## 4. Icon

### Button Styles:
- `UIDecorator.setSuccessButton`: Green button.  
- `UIDecorator.setNormalButton`: Blue button.  
- `UIDecorator.setDangerButton`: Red button.
```java
UIDecorator.setSuccessButton(create_btn, UIDecorator.ADD(), null);
UIDecorator.setNormalButton(update_btn, UIDecorator.EDIT(), null);
UIDecorator.setDangerButton(delete_btn, UIDecorator.DELETE(), null);
```

1. **First Parameter**: The button to which the icon will be added.
2. **Second Parameter**: The icon to attach (use predefined icons from the `UIDecorator` class).
3. **Third Parameter**: The text for the button. Set it to `null` to remove the text.

## 5. Add a New Model

When adding a new model in the `/Model` directory, follow these steps:

### Steps:
1. **Implement `Serializable`**:  
   - Ensure the new model implements the `Serializable` interface for data persistence.

2. **Use `SimpleProperty`**:  
   - Use `SimpleProperty` for real-time updates in JavaFX.

3. **Generate IDs Automatically**:  
   - Use the `Database.IDGenerator` method to auto-generate IDs in the constructor.  
     ```java
     int id = Database.IDGenerator(Item.class);
     ```

4. **Define Getter, Setter, and Property Methods**:  
   - Example implementations:  
     - **Getter**:  
       ```java
       public String getName() {
           return name;
       }
       ```  
     - **Setter**:  
       ```java
       public void setName(String name) {
           this.name = name;
           if (nameProperty == null) nameProperty = new SimpleStringProperty();
           nameProperty.set(name);
       }
       ```  
     - **Property**:  
       ```java
       public StringProperty nameProperty() {
           if (nameProperty == null) nameProperty = new SimpleStringProperty(name);
           return nameProperty;
       }
       ```

5. **Update `Database.class`**:
   - **Add a New List**: Add a new `List<...>` as a variable in `Database.class`.
   - **Initialize the List**: Initialize the list in the constructor.
   - **Save Data**: Include the list in the `saveData()` method.
   - **Load Data**: Load the list in the `loadData()` method.
   - **Getter**: Write a getter for the new list.
   - **DAO Implementation**: Implement the corresponding DAO for the model.

6. **Add a New ID System** (if required):
   - Define a new `Map<Integer, Boolean>` variable to manage IDs.
   - Update the `IDGenerator` class to save, load, and manage the new ID system.

---

## 6. Helpers

The project includes several helper classes for common functionality:

- **`UIDecorator`**: Manages UI decorations and styles.
- **`ImageUtils`**: Provides utility methods to handle images.
- **`TaskUtils`**: Manages multithreading tasks efficiently.
- **`DateUtils`**: Handles date formatting and date-related operations.
- **`InputValidator`**: Includes common input validation methods.
