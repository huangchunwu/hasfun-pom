# java8 流编程

## Stream 函数

- collect(toList()) 方法由 Stream 里的值生成一个列表，是一个及早求值操作。

`
相当于 Arrays.asList("a","b","c");
List<String> collected = Stream.of("a","b","c").collect(Collectors.toList());
 `
 
 - map 函数可以将一个函数一种类型的值转换成另外一种类型，即将一个流中的值转换成一个新的流
`    List<String> a = Arrays.asList("145","267","398");
     List<Integer> aa =  a.stream().map(v->Integer.valueOf(v)).collect(Collectors.toList());`



- filter 遍历数据并检查其中的元素时，可尝试使用 Stream 中提供的新方法 filter，和 map 很像，filter 接受一个函数作为参数，该函数用 Lambda 表达式表示，和 map 很像，filter 接受一个函数作为参数，该函数用 Lambda 表达式表示

`List<String> beginningWithNumbers = Stream.of("a","1abc","abc1").filter( str -> Character.isDigit(str.charAt(0))).collect(Collectors.toList());


- flatMap 方法  将多个 Stream 连接成一个 Stream：

`List<Integer> together = Stream.of(Arrays.asList(1,2), Arrays.asList(3,4)).flatMap(numbers -> numbers.stream()).collect(Collectors.toList());
Assert.assertEquals(Arrays.asList(1,2,3,4), together);`

-  max & min 查找 Stream 中的最大或最小元素

List<Track> tracks = Arrays.asList(new Track("Bakai", 524), new Track("Violets for Your Furs", 378), new Track("Time Was", 451));
Track shortestTrack = tracks.stream().min(Comparator.comparing(track -> track.getLength())).get();
Assert.assertEquals(tracks.get(1), shortestTrack);

min 和 max 方法返回一个 Optional 对象，它代表一个可能存在也可能不存在的值。 为空，那么该值不存在，如果不为空，则该值存在。通过 get 方法取出 Optional 对象的值。
`

- reduce 操作可以实现从一组值中生成一个值。 在上述例子中用到的 count、 min 和 max 方
法， 因为常用而被纳入标准库中。 事实上， 这些方法都是 reduce 操作



`public Set<String> findLongTracks(List<Album> albums) {
    Set<String> trackNames = new HashSet<>();
    for(Album album : albums) {
        for (Track track : album.getTrackList()) {
            if (track.getLength() > 60) {
                String name = track.getName();
                trackNames.add(name);
            }
        }
    } 
    return trackNames;
}


albums.stream()
        .flatMap(album -> album.getTracks())
        .filter(track -> track.getLength() > 60)
        .map(track -> track.getName())
        .forEach(name -> trackNames.add(name));


return albums.stream()
        .flatMap(album -> album.getTracks()).filter(track -> track.getLength() > 60)
        .map(track -> track.getName())
        .collect(Collectors.toSet());

`

# 方法引用
artist -> artist.getName()
Artist::getName
标准语法为 Classname::methodName。需要注意的是，虽然这是一个方法，但不需要在后面加括号

构造函数也有同样的缩写形式
(name, nationality) -> new Artist(name, nationality)
Artist::new

创建了一个字符串型的数组
String[]::new

# 元素顺序

如果集合本身就是无序的，由此生成的流也是无序的。

# Map 循环

artistCache.forEach((key,artist)->{
    System.out.println("Key:" + key);
    System.out.println("Artist Name:" + artist.getName());
});

# 并行化操作
//串行化计算专辑曲目长度
public int serialArraySum() {
    return albums.stream()
            .flatMap(Album::getTracks)
            .mapToInt(Track::getLength)
            .sum();
}
//并行化计算专辑曲目长度
public int parallelArraySum() {
    return albums.parallelStream()
            .flatMap(Album::getTracks)
            .mapToInt(Track::getLength)
            .sum();
}


https://leongfeng.github.io/2016/11/18/java8-function-program-learning/



