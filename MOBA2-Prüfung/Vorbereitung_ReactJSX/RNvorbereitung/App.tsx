import {StatusBar} from 'expo-status-bar';
import React, {useState} from 'react';
import {StyleSheet, Text, View, FlatList, TextInput, Button, Alert, ScrollView} from "react-native";
import SneakerItem from "./Item/SneakerItem";
import Sneakers from './data/sneakers.json'

const Separator = () => (
    <View style={styles.separator}/>
);

export default function App() {
    const [isProductlisteVisible, setProductListeVisible] = useState(true);
    const [wishlist, setWishlist] = useState([]);
    const [minValue, setMinValue] = useState(0);
    const [maxValue, setMaxValue] = useState(999);


    return (
        <View style={styles.container}>
            <Text style={styles.titleFont}>HALLO LUKAS ZOSS</Text>
            {isProductlisteVisible ?
                <View style={styles.containerCol}>
                    <Text style={styles.subTitleFont}>Produkte</Text>
                    <View style={styles.containerRow}>
                        <Button
                            title="<100"
                            color="#777777"
                            onPress={() => {setMinValue(0) ;  setMaxValue(100)}}
                        />
                        <Button
                            title="100-120"
                            color="#777777"
                            onPress={() => {setMinValue(100) ;  setMaxValue(120)}}
                        />
                        <Button
                            title="120-150"
                            color="#777777"
                            onPress={() => {setMinValue(120) ;  setMaxValue(150)}}
                        />
                        <Button
                            title="150-170"
                            color="#777777"
                            onPress={() => {setMinValue(150) ;  setMaxValue(170)}}
                        />
                        <Button
                            title=">170"
                            color="#777777"
                            onPress={() => {setMinValue(170) ;  setMaxValue(999)}}
                        />
                        <Button
                            title="Reset"
                            color="#CC0000"
                            onPress={() => {setMinValue(0) ;  setMaxValue(999)}}
                        />

                    </View>
                    <FlatList
                        style={{paddingHorizontal: 15,
                            width: '100%',
                            height: 600,
                            flexGrow: 0}}
                        data={Sneakers.filter(elem => minValue <= elem.price && maxValue >= elem.price)}
                        renderItem={({item}) => (
                            <><SneakerItem
                                item = {item}
                                wishlist = {wishlist}
                                setWishlist = {setWishlist}
                                />
                                <Separator/></>
                        )}
                    />
                </View>
                :
                <View style={styles.containerCol}>
                    <Text style={styles.subTitleFont}>Wunschliste</Text>
                    <FlatList
                        style={{paddingHorizontal: 15,
                            width: '100%',
                            height: 600,
                            flexGrow: 0}}
                        data={wishlist}
                        renderItem={({item}) => (
                            <><SneakerItem
                                item = {item}
                                wishlist = {wishlist}
                                setWishlist = {setWishlist}
                                />
                                <Separator/></>
                        )}
                    />
                    <Text style={styles.priceFont}>{wishlist.reduce((total, item) => total + item.price, 0)} CHF</Text>
                </View>
            }


            <View style={styles.tabContainer}>
                <Button
                    title="Produkte"
                    color="#BB1100"
                    onPress={() => setProductListeVisible(true)}
                />
                <Button
                    title="Wunschliste"
                    color="#BB1100"
                    onPress={() => setProductListeVisible(false)}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    containerRow: {
        flexDirection: "row",
        alignItems: 'center',
        justifyContent: 'center',
        textAlign: 'center',
        padding: 15,
    },
    containerCol: {
        flexDirection: "column",
        alignItems: 'center',
        justifyContent: 'center',
        textAlign: 'center',
        padding: 15,
    },
    tabContainer: {
        flexDirection: "row",
        alignItems: 'center',
        justifyContent: 'center',
        textAlign: 'center',
        padding: 15,
        width: "90%",
        margin: 10,
    },
    btnSize:{
        borderWidth: 1,
        alignItems:'center',
        borderColor: 'black',
        backgroundColor: '#ecf0f1',
        width: '50%',
    },
    priceFont: {
        color: '#000',
        fontSize: 35,
        textAlign: "right"
    },
    titleFont: {
        color: '#000',
        fontSize: 30,
        textAlign: "center"
    },
    subTitleFont: {
        color: '#000',
        fontSize: 25,
        textAlign: "center"
    },
    separator: {
        marginVertical: 8,
        borderBottomColor: '#737373',
        borderBottomWidth: StyleSheet.hairlineWidth,
    },
});
