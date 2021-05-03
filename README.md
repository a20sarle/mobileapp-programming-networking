
# Rapport för uppgift 6: networking

Till en början skapades lösningen utefter vad som tagits upp under föreläsningen men efter ett tag
ändrades lösningsmetoden då en insåg att många av de delar som tagits upp under genomgången inte
var aktuellt för uppgiften tillhanda.


Från början skapades en ListView, getters, en ny java class och medlemmar till denna och alla dessa tre
användes vidare i den andra lösningsmetoden. Delar som först skapades som inte kom till användning var
exmepelvis gson biblioteket, assets mapp och fil samt WebView. De sistnämnda var relevanta om en velat
hämta datan från en egen .json fil men eftersom en skulle hämta data direkt från en web service kom de
inte till nytta och togs därav bort.


Den aktuella lösningen använde en ListView som fylldes med data genom en TextView där de två kopplades
ihop m.h.a. en adapter. ListView lades i activity_main.xml medan TextView skulle ligga i list_item_textview.xml.
Adaptern initierades i MainActivity.java. Koden nedan skriver ut den hämtade datan ihop med det egenkomponerade
meddelandet i toast:en.
```
adapter = new ArrayAdapter<Mountain>(this, R.layout.list_item_textview,R.id.list_item_textview,item);
ListView myListView = findViewById(R.id.list_view);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name= item.get(position).getName("name");
                int height = item.get(position).getHeight("height");
                String location = item.get(position).getLocation("location");
                String msg = name + " is about " + height + " MASL and located in " + location + ".";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
```
![](assignment6-afterToast.png)

Det fanns minst tre olika block med kod att använda för att få fram den önskade funktionen.
Alla metoderna använde en for-loop och det krävs att en array deklareras för alla de olika sätten.
Metod No.3 nedan är en blandning av lösningsmetod No.1 och No.2 där en tydligt kan se vilka delar
som ersätter varandra i lösningsförslag No.1 respektive alternativ No.2.
```
/* METHOD 1 */
try {
    JSONArray jsonarray = new JSONArray(s);
    for(int i = 0; i < jsonarray.length(); i++){
        JSONObject object = jsonarray.getJSONObject(i);
        String name = object.getString("name");
        int height = object.getInt("size");
        String location = object.getString("location");
        item.add(new Mountain(name, height, location));
        adapter.notifyDataSetChanged();
    }
} catch (JSONException e) {
    Log.e("==>","E:"+e.getMessage());
}
```
```
/* METHOD 2 */
Gson gson=new Gson();
Mountain[] mountains;
mountains=gson.fromJson(s,Mountain[].class);

item.clear();
for(int i=0; i <mountains.length; i++){
    item.add(mountains[i]);
}
adapter.notifyDataSetChanged();
```
```
/* METHOD 3 */
try {
    Gson gson=new Gson();
    Mountain[] mountains;
    mountains=gson.fromJson(s,Mountain[].class);
    item.clear();
    for(int i=0; i <mountains.length; i++){
        item.add(mountains[i]);
    }
    adapter.notifyDataSetChanged();

} catch (JsonSyntaxException e) {
    e.printStackTrace();
}
```


För att kunna hämta data från arrayen behövde en bl.a. använda getters och _private_ _member_ _variables_
i den nya javaklassen kallad Mountain.java. En behövde också använda en JasonTask() samt ge tillgång till
internet.


Om en inte klickat på ett av bergen skulle toasten och meddelandet inte visas och det skulle då sett ut
på följande vis.

![](assignment6-beforeToast.png)
