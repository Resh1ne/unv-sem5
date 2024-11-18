<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Navbar</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>

<!-- Горизонтальная навигационная панель с кнопками -->
<div class="navbar">
  <button class="navbar_button" onclick="navigateTo('http://localhost:8080')">Home</button>
  <button class="navbar_button" onclick="navigateTo('controller?command=exhibition_halls')">Test bottom</button>
</div>

<script>
  function navigateTo(url) {
    window.location.href = url;
  }
</script>