export function translateDishType(name) {
  switch(name) {
    case "BREAKFEST":
      return "Śniadanie";
    case "BRUNCH":
      return "II śniadanie"
    case "DINNER":
      return "Obiad";
    case "TEA":
      return "Podwieczorek";
    case "SUPPER":
      return "Kolacja";
    case "PRE_WORKOUT":
      return "Przedtreningówka";
    case "POST_WORKOUT":
      return "Potreningówka";
    default:
      return name;
  }
}
