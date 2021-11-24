function isNumeric(str) {
        if (typeof str != "string") return false;
        return !isNaN(str) && !isNaN(parseFloat(str));
    }

//init board
    let now;
    let board = JXG.JSXGraph.initBoard('box1', {
        axis: true,
        boundingbox: [-5.5, 5.5, 5.5, -5.5]
    });

//sliders
    let r = board.create('slider', [[1, 3], [3, 3], [1, 4, 3]], {name: 'R', snapWidth: 0.25, visible: false});
    let changeR = (newR) => {
        console.log("changing R: " + newR)
        r.setValue(newR);
    }

    function updateR(){
        console.log("changing R: " + document.getElementById('main-form:r_chooser'));
        changeR(document.getElementById('main-form:r_chooser').value);
    }
    console.log(document.getElementById('main-form:r_chooser'));
    let getR = document.getElementById('main-form:r_chooser').value;
    //console.log(getR);
    changeR(getR);
    //console.log(r.Value());
    let R = () => r.Value();
    let minusR = () => -r.Value();
    let minusHalfR = () => -r.Value() / 2;

    const getXY = (e, i) => {
        let currentPos = board.getCoordsTopLeftCorner(e, i),
            absolutePos = JXG.getPosition(e, i),
            dx = absolutePos[0] - currentPos[0],
            dy = absolutePos[1] - currentPos[1];

        let jxgCoordinate = new JXG.Coords(JXG.COORDS_BY_SCREEN, [dx, dy], board);
        let x = jxgCoordinate.usrCoords[1].toFixed(2);
        let y = jxgCoordinate.usrCoords[2].toFixed(2);
        return {x, y};
    }

    let current = CodeMirror.fromTextArea(document.getElementById('current'), {
        lineNumbers: true,
        styleActiveSelected: true,
        mode: 'text',
        readOnly: true
    })

    current.setSize(null, 30);
    current.setValue('(0, 0)');


// second corner (triangle)
    board.create('polygon', [[0, 0], [minusR, 0], [0, R]], {
        fillColor: '#e74c3c',
        strokeColor: 'none',
        withLines: false,
        vertices: {visible: false}
    });

//third corner (square)
    board.create('polygon', [[0, 0], [minusHalfR, 0], [minusHalfR, minusR], [0, minusR]], {
        fillColor: '#3498db',
        strokeColor: 'none',
        withLines: false,
        vertices: {visible: false}
    });

//quarter corner (quarter circle)
    let quarterCircle = X => -Math.sqrt((r.Value() - X) * (r.Value() + X));
    board.create('functiongraph', [quarterCircle, 0, R], {
        fillColor: '#16a085',
        fillOpacity: 0.3,
        strokeColor: 'none',
        highlight: false,
        withLines: false,
        vertices: {visible: false},
    });

    board.create('polygon', [[0, 0], [R, 0], [0, minusR]], {
        fillColor: '#16a085',
        strokeColor: 'none',
        withLines: false,
        vertices: {visible: false},
    })

    let drawPoints = () => {
        //console.log(document.getElementById("resultTable"));
        //console.log(document.getElementById("resultTable").getElementsByTagName("td"));
        let coordinates = Array.prototype.slice.call(document.getElementById("resultTable").getElementsByTagName("td"));
        for( let i = 0 ; i < coordinates.length; i += 4){
            if(coordinates[i].innerText !== '') {
                board.create('point', [Number(coordinates[i].innerHTML), Number(coordinates[i + 1].innerHTML)], {
                    color: coordinates[i + 3].innerText === 'true' ? 'green' : 'red',
                    label: {visible: false}
                })
            }
        }
    }
    drawPoints();

    document.getElementById("main-form:j_idt32").addEventListener("")

    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("main-form:r_chooser").addEventListener("change", (e) => {
            console.log(e);
            console.log(e.target.value);
            changeR(e.target.value);
        })
    });
   /* document.getElementById('main-form:r_chooser').addEventListener("change", (e, i) => {
        console.log(e);
        console.log(e.target.value);
        changeR(e.target.value);
    })*/

    document.getElementById('box1').addEventListener('mousemove', (e, i) => {
        let {x, y} = getXY(e, i);
        current.setValue('(' + x + ', ' + y + ')');
    });

    document.getElementById('box1').addEventListener('mousedown', (e, i) => {
        let {x, y} = getXY(e, i);
        if(x < -4 || x > 4){
            alert("X must be in range [-4;4]!")
            return ;
        }
        if(y > 5 || y < -5){
            alert("Y must be in range [-5;5]!");
            return ;
        }
        document.getElementById("hidden-form:x_hidden_chooser").value = x;
        document.getElementById("hidden-form:y_hidden_chooser").value = y;
        document.getElementById("hidden-form:hidden-submit").click();
    });


    let overviewInput = document.getElementById('overview');
    let constraintInput = document.getElementById('constraint');
    let notationInput = document.getElementById('notation');

    let overviewContent = document.getElementById('overviewContent');
    let constraintContent = document.getElementById('constraintContent');
    let notationContent = document.getElementById('notationContent');

    overviewInput.addEventListener('click', () => {
        if (!overviewInput.classList.contains('checked')) {
            overviewInput.classList.toggle('checked');
            overviewContent.style.display = 'block';
        }
        if (constraintInput.classList.contains('checked')) {
            constraintInput.classList.toggle('checked');
            constraintContent.style.display = 'none';

        }
        if (notationInput.classList.contains('checked')) {
            notationInput.classList.toggle('checked');
            notationContent.style.display = 'none';

        }
    })

    constraintInput.addEventListener('click', () => {
        if (overviewInput.classList.contains('checked')) {
            overviewInput.classList.toggle('checked');
            overviewContent.style.display = 'none';
        }
        if (!constraintInput.classList.contains('checked')) {
            constraintInput.classList.toggle('checked');
            constraintContent.style.display = 'block';

        }
        if (notationInput.classList.contains('checked')) {
            notationInput.classList.toggle('checked');
            notationContent.style.display = 'none';
        }
    })

    notationInput.addEventListener('click', () => {
        if (overviewInput.classList.contains('checked')) {
            overviewInput.classList.toggle('checked');
            overviewContent.style.display = 'none';

        }
        if (constraintInput.classList.contains('checked')) {
            constraintInput.classList.toggle('checked');
            constraintContent.style.display = 'none';

        }
        if (!notationInput.classList.contains('checked')) {
            notationInput.classList.toggle('checked');
            notationContent.style.display = 'block';
        }
    })