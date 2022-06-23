
import SwiftUI
import CoreData

struct ContentView: View {
    
    @State var items:[Sneaker] = Array()
    
    var body: some View {
        TabView {
            ProductView(items: $items)
                .tabItem {
                    Label("Products", systemImage: "list.dash")
                }
            FavoritesView(items: $items)
                .tabItem {
                    Label("Favorites", systemImage: "star.fill")
                }
        }.onAppear(){
            items = loadJson()
        }
    }
    
    struct ProductView: View {
        @Binding var items:[Sneaker]
        
        @State var filterCondition : String = ""
        var filterOptions : Array<String> = ["<100", "100-120", "120-150", "150-170", ">170"]
        
        func filterPrice(items: [Sneaker]) -> [Sneaker] {
            switch filterCondition {
            case "<100":
                return items.filter{item in item.price < 100}
            case "100-120":
                return items.filter{item in item.price >= 100 && item.price < 120}
            case "120-150":
                return items.filter{item in item.price >= 120 && item.price < 150}
            case "150-170":
                return items.filter{item in item.price >= 150 && item.price < 170}
            case ">170":
                return items.filter{item in item.price >= 170}
            default:
                return items
            }
        }
        
        var body: some View{
            VStack{
                HStack{
                    Button("<100"){
                        filterCondition = "<100"
                    }.buttonStyle(FilledButton())
                    Button("100-120"){
                        filterCondition = "100-120"
                    }.buttonStyle(FilledButton())
                    Button("120-150"){
                        filterCondition = "120-150"
                    }.buttonStyle(FilledButton())
                    Button("150-170"){
                        filterCondition = "150-170"
                    }.buttonStyle(FilledButton())
                    Button(">170"){
                        filterCondition = ">170"
                    }.buttonStyle(FilledButton())
                    Button("Clear"){
                        filterCondition = ""
                    }.buttonStyle(FilledButton())
                }
                List(filterPrice(items: items), id: \.productId) {item in
                    HStack(
                        alignment: .top,
                        spacing: 10
                    ){
                        Image(String(item.image1.prefix(8)))
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 50, height: 50)
                        //Image("./sneakers/\(item.image2)").resizable()
                        VStack(
                            alignment: .leading,
                            spacing: 10
                        ){
                            Text(item.displayName)
                            Text(item.color)
                        }
                        Spacer()
                        Text("\(String(item.price)) CHF")
                        Image(systemName: item.fav! ? "star.fill" : "star")
                            .onTapGesture {
                                addRemoveFromFavorites(id: item.productId, list: &items)
                            }
                    }
                }
            }
        }
    }
    struct FavoritesView: View {
        @Binding var items:[Sneaker]
        var body: some View{
            if(items.filter{i in i.fav! == true}.isEmpty){
                Text("No Favorites").font(.largeTitle)
            } else {
                VStack{
                    
                    
                    List(items.filter{i in i.fav! == true}, id: \.productId){ item in
                        HStack(
                            alignment: .top,
                            spacing: 10
                        ){
                            Image(String(item.image1.prefix(8)))
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 50, height: 50)
                            //Image("./sneakers/\(item.image2)").resizable()
                            VStack(
                                alignment: .leading,
                                spacing: 10
                            ){
                                Text(item.displayName)
                                Text(item.color)
                            }
                            Spacer()
                            Text("\(String(item.price)) CHF")
                            Image(systemName: item.fav! ? "star.fill" : "star")
                                .onTapGesture {
                                    addRemoveFromFavorites(id: item.productId, list: &items)
                                }
                        }
                    }
                    Text("Total: \(items.filter{i in i.fav! == true}.reduce(0,{x,y in x + y.price})) CHF")}
            }
        }
    }
}

struct ResponseData: Decodable {
    var sneaker: [Sneaker]
}
struct Sneaker : Decodable {
    var productId: String
    var displayName: String
    var altText: String
    var image1: String
    var image2: String
    var price: Int
    var color: String
    var fav: Bool? = false
}

func loadJson() -> [Sneaker] {
    do {
        let url = Bundle.main.url(forResource: "./sneakers/sneakers", withExtension: "json")!
        let jsonData = try Data(contentsOf: url)
        let decoder = JSONDecoder()
        var r = try decoder.decode([Sneaker].self, from: jsonData)
        for index in r.indices {
            r[index].fav = false
        }
        print(r)
        return r
    } catch let err {
        print(err)
        fatalError("Couldn't load file")
    }
}

func addRemoveFromFavorites(id: String, list: inout [Sneaker]) {
    let index = list.firstIndex(where: {$0.productId == id})!
    list[index].fav = !list[index].fav!
}

struct FilledButton: ButtonStyle {
    func makeBody (configuration: Configuration) -> some View {
        configuration
            .label
            .foregroundColor(.white)
            .padding(3)
            .background(Color.accentColor)
            .cornerRadius(4)
    }
}
