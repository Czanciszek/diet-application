export function translateDishType(name) {
  switch(name) {
    case "BREAKFAST":
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
    case "OVERNIGHT":
      return "Posiłek nocny";
    default:
      return name;
  }
}

export function translateDayType(name) {
  switch(name) {
    case "MONDAY":
      return "Poniedziałek";
    case "TUESDAY":
      return "Wtorek"
    case "WEDNESDAY":
      return "Środa";
    case "THURSDAY":
      return "Czwartek";
    case "FRIDAY":
      return "Piątek";
    case "SATURDAY":
      return "Sobota";
    case "SUNDAY":
      return "Niedziela";
    default:
      return name;
  }
}
