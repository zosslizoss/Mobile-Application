import React, {useState} from 'react'
import {StyleSheet, View, Text, Image, TouchableHighlight} from 'react-native';


function SneakerItem({item, wishlist, setWishlist}: any) {

        return (
            <TouchableHighlight underlayColor={'#bbb'}>
                <View style={styles.containerRow}>
                    <Image style={styles.image} source={require("../Data/" + item.image1)}/>
                    <Image style={styles.image} source={require("../Data/" + item.image2)}/>
                    <View style={styles.containerCol1}>
                        <Text style={styles.largeFont}>{item.displayName}</Text>
                        <Text style={styles.mediumFont}>{item.color}</Text>
                    </View>
                    <View style={styles.containerCol2}>
                        <Text style={styles.priceFont}>{item.price + " CHF"}</Text>
                    </View>
                    {wishlist.includes(item) ?
                        <View>
                            <text onClick={event => setWishlist(wishlist.filter((value: { productId: any; }) => value.productId != item.productId))}>&#9733;</text>
                        </View>
                    :
                        <View>
                            <text onClick={event => setWishlist([...wishlist, item])}>&#9734;</text>
                        </View>
                    }
                </View>
            </TouchableHighlight>
        );
    }

    const styles = StyleSheet.create({
        containerRow: {
            flex: 1,
            justifyContent: "space-between",
            flexDirection: "row",
            alignItems: "center"
        },
        containerCol1: {
            flexDirection: "column",
            width: 300,
        },
        containerCol2: {
            flexDirection: "column",

        },
        roles: {
            color: '#fff'
        },
        image: {
            width: 80,
            height: 80,
            borderRadius: 5,
            marginRight: 5,
            marginTop: 5,
            marginLeft: 5,
            marginBottom: 5,
        },
        largeFont: {
            color: '#000',
            fontSize: 20
        },
        priceFont: {
            color: '#000',
            fontSize: 20,
            textAlign: "right"
        },
        mediumFont: {
            color: '#000',
            fontSize: 16
        },
        smallFont: {
            color: '#000',
            fontSize: 10
        }
    })

    export default SneakerItem;
