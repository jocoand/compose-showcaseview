<p>
  <img src="https://img.shields.io/badge/version-1.0.0-blue.svg" alt="version">
  <img src="https://img.shields.io/badge/platform-android-brightgreen.svg" alt="platform">
</p>

## 🌟 Compose Showcase View
A simple showcase library for highlighting components and showing a dialog on it. 

<p>
  <img src="https://github.com/user-attachments/assets/2e02b2b7-181d-47b0-b95d-b3e37e146785" width="300" alt="preview">
</p>

### Installation
- Gradle
  ```
  implementation("io.github.jocoand:compose-showcaseview:1.0.0")
  ```

### Usage
- Create your Showcase dialog
- ```
    @Composable
    fun MyShowcaseDialog(text: String, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onClick
            ) {
                Text("Nice !")
            }
        }
    }
  ```
  
- Get your target component `LayoutCoordinates` using `onGloballyPositioned` modifier
- ```
  val targets = remember { mutableStateMapOf<String, LayoutCoordinates>() }
  var greetingShowcaseVisible by remember { mutableStateOf(false) }
  
  ...
  // Target component to be highlighted
  Greeting(
      modifier = Modifier
          .align(Alignment.End)
          .onGloballyPositioned { coordinates ->
              targets[KEY_GREETING] = coordinates
          },
      name = "Andy",
      onClick = { greetingShowcaseVisible = true }
  )
  ```

- Declare `ShowcaseView`, use `visible` to set ShowcaseView visibility
- ```
  targets[KEY_SHARE]?.let { coordinates ->
      ShowcaseView(
          visible = visibleShowcase == VisibleShowcase.Share,
          targetCoordinates = coordinates,
      ) {
          MyShowcaseDialog(
              text = "Click to Share the article",
              onClick = { visibleShowcase = VisibleShowcase.None }
          )
      }
  }
  ```
  You may want to put you ShowcaseView in your top most componenent to make the overlay cover the whole screen
  ```
    Box(modifier = Modifier.fillMaxSize()) {
      Scaffold(
          ...
      ) { innerPadding ->
      }
        targets[KEY_GREETING]?.let { coordinates ->
            ShowcaseView(
                visible = greetingShowcaseVisible,
                targetCoordinates = coordinates,
            ) {
                MyShowcaseDialog(
                    text = "Hey, this is Greetings showcase",
                    onClick = { greetingShowcaseVisible = false }
                )
            }
        }
    }
  ```
### Config
- `position`
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/b17f8515-3d5a-4236-ae47-18d3f90950e3" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/149a7987-4c17-49e6-8a02-38f1053b9067" width="200">
      </td>
    </tr>
    <tr>
      <td align="center">Top</td>
      <td align="center">Bottom</td>
    </tr>
  </table>

  `Default`: relative to target position

- `alignment`
  <table>
    <tr>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/b8b4f44e-e662-48f4-ab1f-9ec1a55190bd" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/06452f70-dfe9-4851-a542-234e35355ecf" width="200">
      </td>
      <td align="center">
        <img src="https://github.com/user-attachments/assets/9ed60ef7-b696-4019-801e-717c0256ccc9" width="200">
      </td>
    </tr>
    <tr>
      <td align="center">Start</td>
      <td align="center">Center</td>
      <td align="center">End</td>
    </tr>
  </table>

  `Default`: relative to target position

### 💡Inspired by
- https://github.com/SimformSolutionsPvtLtd/SSComposeShowCaseView

### 🎨 Sample
- [MainActivity](https://github.com/jocoand/compose-showcaseview/blob/main/app/src/main/java/com/joco/composeshowcaseview/MainActivity.kt)
