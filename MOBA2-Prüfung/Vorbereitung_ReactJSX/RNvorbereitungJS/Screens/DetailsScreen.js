import React from 'react';
import {StyleSheet, Text, View, FlatList, TextInput, Button, Alert, Image} from "react-native";

const Separator = () => (
    <View style={styles.separator} />
);

const DetailsScreen = ({ navigation, route }) => {
    const item = route.params?.element
    return (
        <View style={styles.containerCol}>
            <Image style={styles.image} source={require("../Data/" + item.image1)}/>
            <Separator/>
            <Image style={styles.image} source={require("../Data/" + item.image2)}/>
            <Separator/>
            <Text style={styles.largeFont}>{item.displayName}</Text>
            <Text style={styles.mediumFont}>{item.color}</Text>
            <Text style={styles.smallFont}>{item.altText}</Text>
            <Separator/>
            <Text style={styles.largeFont}>{item.price + "CHF"}</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    containerRow: {
        flex: 1,
        justifyContent: 'center',
        flexDirection: "row",
        marginHorizontal: 16,
        width: 300
    },
    containerCol:{
        flexDirection: "column",
        alignItems: 'center',
        justifyContent: 'center',
        textAlign: 'center',
        padding: 15,
    },
    separator: {
        marginVertical: 8,
        borderBottomColor: '#737373',
        borderBottomWidth: StyleSheet.hairlineWidth,
    },
    image: {
        width: 300,
        height: 300,
    },
    largeFont: {
        color: '#000',
        fontSize: 22
    },
    mediumFont: {
        color: '#000',
        fontSize: 16
    },
    smallFont: {
        color: '#000',
        fontSize: 10
    },
});

export default DetailsScreen;
